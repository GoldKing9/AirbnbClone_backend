package dbDive.airbnbClone.api.accommodation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SearchRequest(
        String mainAddress,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate checkout,
        int minPrice,
        int maxPrice,
        int guest,
        int bathroom,
        int bedroom,
        int bed
) {
}
