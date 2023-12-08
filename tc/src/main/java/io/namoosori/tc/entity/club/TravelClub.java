package io.namoosori.tc.entity.club;

import io.namoosori.tc.entity.BaseTimeEntity;
import io.namoosori.tc.service.dto.TravelClubDto;
import io.namoosori.tc.shared.NameValue;
import io.namoosori.tc.shared.NameValueList;
import io.namoosori.tc.util.DateUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "TRAVEL_CLUB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "CLUB_SEQ_GENERATOR",
        sequenceName = "CLUB_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Proxy(lazy = false)
public class TravelClub extends BaseTimeEntity {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLUB_SEQ_GENERATOR")
    @Column(name = "CLUB_ID", nullable = false)
    private long clubId;

    @Column(name = "CLUB_NAME", nullable = false)
    @Size(min = 3)
    private String clubName;

    @Column(name = "CLUB_INTRO", nullable = false)
    @Size(min = 10)
    private String clubIntro;

    @OneToMany(mappedBy = "club",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMembership> memberships = new ArrayList<>();

    @OneToOne(mappedBy = "travelClub",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private SocialBoard socialBoard;

    public TravelClubDto EntityToDto() {
        //
        return TravelClubDto.builder()
                .clubId(this.clubId)
                .clubName(this.clubName)
                .clubIntro(this.clubIntro)
                .createdDate(this.getCreatedDate())
                .build();
    }

    public List<ClubMembership> getMembershipList() {
        //
        return this.memberships;
    }
}
