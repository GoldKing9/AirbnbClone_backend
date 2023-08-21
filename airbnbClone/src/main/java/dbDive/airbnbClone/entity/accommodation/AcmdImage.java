package dbDive.airbnbClone.entity.accommodation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcmdImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String imgKey; // imgKey는 s3삭제할 때 필요하다!

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acmd_id")
    @JsonBackReference
    private Accommodation accommodation;

    public AcmdImage(String imageUrl, String imgKey) {
        this.imageUrl = imageUrl;
        this.imgKey = imgKey;
    }
}
