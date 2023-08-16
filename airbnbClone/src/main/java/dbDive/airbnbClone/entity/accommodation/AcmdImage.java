package dbDive.airbnbClone.entity.accommodation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AcmdImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String imgKey; // imgKey는 s3삭제할 때 필요하다!

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acmd_id")
    private Accommodation accommodation;
}
