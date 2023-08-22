package dbDive.airbnbClone.repository.reservation;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbDive.airbnbClone.api.reservation.dto.HostReservationDto;
import dbDive.airbnbClone.api.reservation.dto.ReservationDto;
import dbDive.airbnbClone.api.reservation.dto.SelectReservationDto;
import dbDive.airbnbClone.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dbDive.airbnbClone.entity.accommodation.QAccommodation.accommodation;
import static dbDive.airbnbClone.entity.accommodation.QAcmdImage.acmdImage;
import static dbDive.airbnbClone.entity.resevation.QReservation.reservation;
import static dbDive.airbnbClone.entity.user.QUser.user;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<ReservationDto> findAllReservations(Pageable pageable, AuthUser authUser) {
        List<ReservationDto> reservations = queryFactory
                .select(Projections.constructor(ReservationDto.class,
                        accommodation.id,
                        reservation.user.id,
                        accommodation.mainAddress,
                        user.username,
                        reservation.checkIn,
                        reservation.checkOut))
                .from(accommodation)
                .join(reservation).on(reservation.accommodation.id.eq(accommodation.id))
                .join(user).on(accommodation.user.id.eq(user.id))
                .where(reservation.isDeleted.eq(false),reservation.user.id.eq(authUser.getUser().getId()))
                .orderBy(reservation.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> resultId = new ArrayList<>();
        for (ReservationDto dto : reservations) {
            resultId.add(dto.getAccommodationId());
        }

        Map<Long, List<String>> acmdImages = queryFactory
                .select(accommodation.id, acmdImage.imageUrl)
                .from(accommodation)
                .join(acmdImage).on(accommodation.id.eq(acmdImage.accommodation.id))
                .where(accommodation.id.in(resultId))
                .orderBy(acmdImage.id.asc())
                .transform(GroupBy.groupBy(accommodation.id)
                        .as(GroupBy.list(acmdImage.imageUrl))
                );

        reservations.forEach(r ->
                r.setImages(acmdImages.getOrDefault(r.getAccommodationId(), new ArrayList<>()))
        );

        Long count = queryFactory.select(reservation.count())
                .from(reservation)
                .join(user).on(user.id.eq(reservation.user.id))
                .where(user.id.eq(authUser.getUser().getId()))
                .fetchOne();

        return new PageImpl<ReservationDto>(reservations, pageable, count);
    }

    @Override
    public SelectReservationDto findSelectReservation(Long reservationId, AuthUser authUser) {
        SelectReservationDto selectReservationDto = queryFactory
                .select(Projections.constructor(SelectReservationDto.class,
                        accommodation.id,
                        reservation.checkIn,
                        reservation.checkOut,
                        user.id,
                        user.username,
                        user.userDescription,
                        reservation.totalPrice))
                .from(reservation)
                .join(user).on(user.id.eq(reservation.user.id))
                .join(accommodation).on(accommodation.id.eq(reservation.accommodation.id))
                .where(reservation.id.eq(reservationId)
                        ,(reservation.isDeleted.eq(false))
                        ,reservation.user.id.eq(authUser.getUser().getId()))
                .orderBy(reservation.createdAt.desc())
                .fetchOne();
        Long accommodationId = selectReservationDto.getAccommodationId();

        List<String> acmdImages = queryFactory
                .select(
                        acmdImage.imageUrl)
                .from(acmdImage)
                .where(acmdImage.accommodation.id.eq(accommodationId))
                .fetch();

        selectReservationDto.setImages(acmdImages);
        return selectReservationDto;
    }

    @Override
    public PageImpl<HostReservationDto> findHostReservation(Pageable pageable,AuthUser authUser) {
        /**
         * 1. 등록된 accommodation에서 userId로 accommodationId 찾기
         * 2. accommodationId 으로 같은 accommodationId 을 가지고 있는 acmd_image, reservation 찾기
         * 3. 상태 체크
         * - checkOut > LocalDate.now() && isDeleted == false, 예약중
         * - checkOut < LocalDate.now() && isDeleted == false, 이용완료
         * - isDeleted == true, 취소
         */

        List<HostReservationDto> hostReservationDto = queryFactory
                .select(Projections.constructor(HostReservationDto.class,
                        reservation.id,
                        user.id,
                        user.username,
                        reservation.checkIn,
                        reservation.checkOut,
                        accommodation.id,
                        accommodation.acmdName,
                        acmdImage.imageUrl.min(),
                        new CaseBuilder()
                                .when(reservation.checkOut.after(LocalDate.now()).and(reservation.isDeleted.eq(false))).then("예약 중")
                                .when(reservation.checkOut.before(LocalDate.now()).and(reservation.isDeleted.eq(false))).then("이용완료")
                                .otherwise("예약 취소"),
                        reservation.totalPrice))
                .from(reservation)
                .leftJoin(user).on(user.id.eq(reservation.user.id))
                .leftJoin(accommodation).on(accommodation.id.eq(reservation.accommodation.id))
                .leftJoin(acmdImage).on(acmdImage.accommodation.id.eq(accommodation.id))
                .where(accommodation.user.id.eq(authUser.getUser().getId()))
                .groupBy(reservation.id)
                .orderBy(reservation.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(reservation.count())
                .from(reservation)
                .join(accommodation).on(reservation.accommodation.id.eq(accommodation.id))
                .where(accommodation.user.id.eq(authUser.getUser().getId()))
                .fetchOne();

        return new PageImpl<HostReservationDto>(hostReservationDto, pageable, count);
    }
}


