package dbDive.airbnbClone.api.accommodation.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class AccommodationEditDto {
    private int guest;
    private int bedroom;
    private int bed;
    private int bathroom;
    private List<String> deleteImageKey;
    private String acmdName;
    private String acmdDescription;
    private int price;

}
