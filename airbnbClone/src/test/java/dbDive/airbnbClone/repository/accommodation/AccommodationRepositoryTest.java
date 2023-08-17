package dbDive.airbnbClone.repository.accommodation;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dbDive.airbnbClone.api.accommodation.dto.AccommodationDataDto;
import dbDive.airbnbClone.api.accommodation.dto.request.SearchRequest;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.resevation.Reservation;
import dbDive.airbnbClone.entity.review.Review;
import dbDive.airbnbClone.repository.reservation.ReservationRepository;
import dbDive.airbnbClone.repository.review.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static dbDive.airbnbClone.entity.accommodation.QAccommodation.accommodation;
import static dbDive.airbnbClone.entity.resevation.QReservation.reservation;

@SpringBootTest
class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    //    @BeforeEach
    void setup() {
        for (int i = 1; i <= 10; i++) {
            Accommodation acmd = Accommodation.builder()
//                    .guest((int) (Math.random() * 4) + 1)
//                    .bedroom((int) (Math.random() * 4) + 1)
//                    .bed((int) (Math.random() * 4) + 1)
//                    .bathroom((int) (Math.random() * 4) + 1)
//                    .price(((int) (Math.random() * 4) + 1) * 1000)
                    .guest(i)
                    .bedroom(i)
                    .bed(i)
                    .bathroom(i)
                    .price(i)
                    .build();
            accommodationRepository.save(acmd);

            if (i == 2) {
                Reservation res = Reservation.builder()
                        .accommodation(acmd)
                        .checkIn(LocalDate.of(2023, 5, 3))
                        .checkOut(LocalDate.of(2023, 5, 23))
                        .build();
                reservationRepository.save(res);

                Review review = Review.builder()
                        .accommodation(acmd)
                        .rating(3)
                        .build();
                Review review2 = Review.builder()
                        .accommodation(acmd)
                        .rating(2)
                        .build();
                Review review3 = Review.builder()
                        .accommodation(acmd)
                        .rating(1)
                        .build();
                reviewRepository.save(review);
                reviewRepository.save(review2);
                reviewRepository.save(review3);

            }
        }
    }

    @Test
    void test() {
        Pageable pageable = PageRequest.of(0, 100);
        SearchRequest request = new SearchRequest(null, LocalDate.of(2023, 6, 3), LocalDate.of(2023, 6, 23), 0, Integer.MAX_VALUE, 1, 1, 1, 1);
        PageImpl<AccommodationDataDto> search = accommodationRepository.search(pageable, request);
        for (AccommodationDataDto accommodationDataDto : search) {
            System.out.println("accommodationDataDto = " + accommodationDataDto);
        }
        System.out.println(search.getTotalPages());
        System.out.println(search.getNumber());

    }

}
