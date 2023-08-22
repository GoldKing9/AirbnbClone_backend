package dbDive.airbnbClone.entity.accommodation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dbDive.airbnbClone.entity.BaseTimeEntity;
import dbDive.airbnbClone.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Accommodation SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Accommodation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainAddress;
    private String detailAddress;
    private int bed;
    private int bedroom;
    private int bathroom;
    private int guest;
    private String acmdName;
    private String acmdDescription;
    private int price;
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accommodation")
    @JsonManagedReference
    private List<AcmdImage> images = new ArrayList<>();

    public void updateAccommodationDetails(int bed, int bedroom, int bathroom, int guest, String acmdName, String acmdDescription, int price, User user) {
        this.bed = bed;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.guest = guest;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.price = price;
        this.user = user;
    }

    @Builder
    public Accommodation(String mainAddress, String detailAddress, int bed, int bedroom, int bathroom, int guest, String acmdName, String acmdDescription, int price, boolean isDeleted, User user) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.bed = bed;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.guest = guest;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.price = price;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public Accommodation(String mainAddress, int price, String detailAddress, String acmdName, String acmdDescription, int guest, int bedroom, int bed, int bathroom, User user) {
        this.mainAddress = mainAddress;
        this.price = price;
        this.detailAddress = detailAddress;
        this.acmdName = acmdName;
        this.acmdDescription = acmdDescription;
        this.guest = guest;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bathroom = bathroom;
        this.user = user;
    }

    public void addImage(AcmdImage image) {
        this.images.add(image);
        image.setAccommodation(this);
    }
    public void removeImage(AcmdImage image) {
        this.images.remove(image);
        image.setAccommodation(null);
    }

    public void clearImage() {
        this.images.clear();
    }
}