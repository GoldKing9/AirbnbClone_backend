package dbDive.airbnbClone.api.accommodation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SearchRequest(
        String mainAddress,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate checkout,
        Integer minPrice,
        Integer maxPrice,
        Integer guest,
        Integer bathroom,
        Integer bedroom,
        Integer bed
) {
}
