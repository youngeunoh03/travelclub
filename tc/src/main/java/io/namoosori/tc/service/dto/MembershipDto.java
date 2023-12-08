package io.namoosori.tc.service.dto;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.entity.vo.RoleInClub;
import io.namoosori.tc.util.DateUtil;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDto implements Serializable {
    //
    private Long membershipId;
    private Long clubId;
    private Long memberId;
    private String memberEmail;
    private RoleInClub role;
    private LocalDateTime createdDate;

//    private ClubMembership membership;
//    private TravelClub club;

//    public MembershipDto(ClubMembership membership) {
//        this.membership = new ClubMembership();
//    }

    public MembershipDto(ClubMembership clubMembership) {
        //
    }

    public MembershipDto(Long clubId, Long memberId, String memberEmail, RoleInClub role) {
        //
        this.memberId = memberId;
        this.clubId = clubId;
        this.memberEmail = memberEmail;
        this.role = role;
    }
}
