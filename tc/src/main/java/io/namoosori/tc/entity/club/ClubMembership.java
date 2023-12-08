package io.namoosori.tc.entity.club;

import io.namoosori.tc.entity.BaseTimeEntity;
import io.namoosori.tc.entity.vo.RoleInClub;
import io.namoosori.tc.util.DateUtil;
import io.namoosori.tc.utility.InvalidEmailException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Data
@Setter
@Getter
@Table(name = "CLUB_MEMBERSHIP")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "MEMBERSHIP_SEQ_GENERATOR",
        sequenceName = "MEMBERSHIP_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Proxy(lazy = false)
public class ClubMembership extends BaseTimeEntity {
    //
    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBERSHIP_SEQ_GENERATOR")
    private Long membershipId;

    @Column
    @NotNull
    private String memberEmail;

    @Column
    @NotNull
    private RoleInClub role;

//    @Column(nullable = false)
//    private String joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBERSHIP_CLUB", referencedColumnName = "club_Id")
    private TravelClub club;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBERSHIP_MEMBER")
    private CommunityMember member;

    public String getClubId() {
        String id = String.valueOf(club.getClubId());
        return id;
    }

    public String getMemberId() {
        String id = String.valueOf(member.getMemberId());
        return id;
    }

}
