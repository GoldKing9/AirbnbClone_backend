package dbDive.airbnbClone.repository.accommodation;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbDive.airbnbClone.api.accommodation.dto.response.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.response.ImageDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.api.accommodation.dto.response.DetailAcmdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dbDive.airbnbClone.entity.accommodation.QAccommodation.accommodation;
import static dbDive.airbnbClone.entity.accommodation.QAcmdImage.acmdImage;
import static dbDive.airbnbClone.entity.resevation.QReservation.reservation;
import static dbDive.airbnbClone.entity.review.QReview.review;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public DetailAcmdResponse findAccommodation(Long accommodationId) {
        DetailAcmdResponse detailAcmdResponse = jpaQueryFactory.select(Projections.constructor(DetailAcmdResponse.class,
                        accommodation.mainAddress,
                        accommodation.detailAddress,
                        accommodation.price,
                        accommodation.user.id,
                        accommodation.user.username,
                        accommodation.user.userDescription,
                        accommodation.guest,
                        accommodation.bed,
                        accommodation.bedroom,
                        accommodation.bathroom,
                        review.rating.avg(),
                        review.count()
                ))
                .from(accommodation)
                .leftJoin(review).on(accommodation.eq(review.accommodation))
                .where(accommodation.id.eq(accommodationId))
                .fetchOne();
        List<ImageDto> images = jpaQueryFactory.select(Projections.constructor(ImageDto.class,
                        acmdImage.imageUrl,
                        acmdImage.imgKey
                ))
                .from(accommodation)
                .leftJoin(acmdImage).on(accommodation.eq(acmdImage.accommodation))
                .where(accommodation.id.eq(accommodationId))
                .fetch();
        detailAcmdResponse.setImages(images);
        return detailAcmdResponse;
    }

    @Override
    public PageImpl<AccommodationDataDto> search(Pageable pageable, SearchRequest request) {
        List<AccommodationDataDto> accommodations = jpaQueryFactory.select(Projections.constructor(AccommodationDataDto.class,
                        accommodation.id,
                        accommodation.mainAddress,
                        accommodation.price,
                        review.rating.avg()
                ))
                .from(accommodation)
                .leftJoin(review).on(accommodation.eq(review.accommodation))
                .where(
                        containMainAddress(request.mainAddress()),
                        periodDate(request.checkIn(), request.checkout()),
                        betweenPrice(request.minPrice(), request.maxPrice()),
                        goeGuest(request.guest()),
                        goeBathroom(request.bathroom()),
                        goeBedroom(request.bedroom()),
                        goeBed(request.bed())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(accommodation.id)
                .fetch();

        fillAccommodationImages(accommodations);

        Long count = jpaQueryFactory.select(
                        accommodation.count()
                )
                .from(accommodation)
                .fetchOne();

        return new PageImpl<>(accommodations, pageable, count);
    }

    private void fillAccommodationImages(List<AccommodationDataDto> accommodations) {
        List<Long> acmdId = accommodations.stream()
                .map(AccommodationDataDto::getAccommodationId)
                .collect(Collectors.toList());

        Map<Long, List<ImageDto>> imageMap = jpaQueryFactory.select(
                        accommodation.id,
                        acmdImage.imageUrl
                )
                .from(accommodation)
                .leftJoin(acmdImage).on(accommodation.eq(acmdImage.accommodation))
                .where(accommodation.id.in(acmdId))
                .transform(GroupBy.groupBy(accommodation.id)
                        .as(GroupBy.list(Projections.constructor(ImageDto.class,
                                acmdImage.imageUrl))));

        accommodations.forEach(a ->
                a.setImages(imageMap.getOrDefault(a.getAccommodationId(), new ArrayList<>()))
        );
    }

    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        BooleanExpression isMinPrice = accommodation.price.goe(minPrice);
        BooleanExpression isMaxPrice = accommodation.price.loe(maxPrice);
        return Expressions.allOf(isMinPrice, isMaxPrice);

    }

    private BooleanExpression goeBed(int bed) {
        return isEmpty(bed) ? null : accommodation.bed.goe(bed);
    }

    private BooleanExpression goeBedroom(int bedroom) {

        return isEmpty(bedroom) ? null : accommodation.bedroom.goe(bedroom);
    }

    private BooleanExpression goeBathroom(int bathroom) {

        return isEmpty(bathroom) ? null : accommodation.bathroom.goe(bathroom);
    }

    private BooleanExpression goeGuest(int guest) {
        return isEmpty(guest) ? null : accommodation.guest.goe(guest);
    }

    private BooleanExpression periodDate(LocalDate checkIn, LocalDate checkout) {
        if (checkIn == null || checkout == null) {
            return null;
        }
        return accommodation.id.notIn(
                JPAExpressions.select(
                                reservation.accommodation.id
                        )
                        .from(reservation)
                        .where(
                                reservation.checkOut.goe(checkIn)
                                        .and(reservation.checkIn.loe(checkout))
                        )
        );
    }

    private BooleanExpression containMainAddress(String mainAddress) {
        return isEmpty(mainAddress) ? null : accommodation.mainAddress.contains(mainAddress);
    }
}
