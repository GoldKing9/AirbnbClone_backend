package dbDive.airbnbClone.repository.review;

import dbDive.airbnbClone.api.review.dto.response.ReviewComment;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    PageImpl<ReviewComment> findAllByAcmdId(Long acmdId, Pageable pageable);
}
