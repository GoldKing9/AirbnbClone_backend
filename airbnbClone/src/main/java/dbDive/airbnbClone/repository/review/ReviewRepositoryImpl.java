package dbDive.airbnbClone.repository.review;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbDive.airbnbClone.api.review.dto.response.ReviewComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static dbDive.airbnbClone.entity.review.QReview.*;
import static dbDive.airbnbClone.entity.user.QUser.user;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PageImpl<ReviewComment> findAllByAcmdId(Long acmdId, Pageable pageable){
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                review.createdAt,
                ConstantImpl.create("%Y년 %m월")
        );

        QueryResults<ReviewComment> result = queryFactory
                .select(Projections.constructor(
                        ReviewComment.class,
                        user.id,
                        user.username,
                        formattedDate,
                        review.comment)
                )
                .from(review)
                .join(review.user, user)
                .where(review.accommodation.id.eq(acmdId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<ReviewComment>(result.getResults(), pageable, result.getTotal());
    }
}
