package dbDive.airbnbClone.repository.user;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbDive.airbnbClone.api.review.dto.response.ReviewComment;
import dbDive.airbnbClone.api.user.dto.response.UserProfileAcmd;
import dbDive.airbnbClone.api.user.dto.response.UserProfileResponse;
import dbDive.airbnbClone.api.user.dto.response.UserReviews;
import dbDive.airbnbClone.entity.user.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static dbDive.airbnbClone.entity.accommodation.QAccommodation.accommodation;
import static dbDive.airbnbClone.entity.accommodation.QAcmdImage.*;
import static dbDive.airbnbClone.entity.review.QReview.review;
import static dbDive.airbnbClone.entity.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<UserReviews> findAllByUserId(Long userId, Pageable pageable) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                review.createdAt,
                ConstantImpl.create("%Y년 %m월")
        );

        QUser reviewUser = new QUser("reviewUser");

        JPQLQuery<String> url = JPAExpressions
                .select(acmdImage.imageUrl)
                .from(acmdImage)
                .where(acmdImage.id.eq(
                        JPAExpressions
                                .select(acmdImage.id.min())
                                .from(acmdImage)
                                .where(acmdImage.accommodation.eq(accommodation))
                ));

        List<UserReviews> result = queryFactory
                .select(Projections.constructor(
                        UserReviews.class,
                        accommodation.id,
                        url,
                        accommodation.acmdName,
                        reviewUser.id,
                        review.comment,
                        reviewUser.username,
                        formattedDate)
                )
                .from(user)
                .join(accommodation).on(accommodation.user.eq(user))
                .join(review).on(review.accommodation.eq(accommodation))
                .join(review.user, reviewUser)
                .where(user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(review.count())
                .from(user)
                .join(accommodation).on(accommodation.user.eq(user))
                .join(review).on(review.accommodation.eq(accommodation))
                .where(user.id.eq(userId))
                .fetchOne();

        return new PageImpl<UserReviews>(result, pageable, total);
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        JPQLQuery<Double> average = JPAExpressions
                .select(review.rating.avg())
                .from(review)
                .where(accommodation.user.id.eq(userId),
                        review.accommodation.eq(accommodation));

        JPQLQuery<Long> commentCnt = JPAExpressions
                .select(review.count())
                .from(review)
                .where(accommodation.user.id.eq(userId), review.accommodation.eq(accommodation));

        UserProfileResponse response = queryFactory
                .select(Projections.constructor(
                        UserProfileResponse.class,
                        user.username,
                        commentCnt,
                        average,
                        user.userDescription
                ))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();


        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                review.createdAt,
                ConstantImpl.create("%Y년 %m월")
        );

        QUser reviewUser = new QUser("reviewUser");

        List<ReviewComment> reviews = queryFactory
                .select(Projections.constructor(
                        ReviewComment.class,
                        reviewUser.id,
                        reviewUser.username,
                        formattedDate,
                        review.comment
                ))
                .from(review)
                .join(review.user, reviewUser)
                .where(review.accommodation.eq(accommodation)
                        .and(accommodation.user.id.eq(userId)))
                .orderBy(review.id.desc())
                .limit(6)
                .fetch();
        
        response.setReviews(reviews);

        JPQLQuery<String> url = JPAExpressions
                .select(acmdImage.imageUrl)
                .from(acmdImage)
                .where(acmdImage.id.eq(
                        JPAExpressions
                                .select(acmdImage.id.min())
                                .from(acmdImage)
                                .where(acmdImage.accommodation.eq(accommodation))
                ));

        JPQLQuery<Double> acmdAvg = JPAExpressions
                .select(review.rating.avg())
                .from(review)
                .where(review.accommodation.eq(accommodation));

        List<UserProfileAcmd> accommodations = queryFactory
                .select(Projections.constructor(
                        UserProfileAcmd.class,
                        accommodation.id,
                        url,
                        acmdAvg,
                        accommodation.mainAddress
                ))
                .from(accommodation)
                .where(accommodation.user.id.eq(userId))
                .orderBy(accommodation.id.desc())
                .limit(10)
                .fetch();

        response.setAccommodations(accommodations);

        return response;
    }
}
