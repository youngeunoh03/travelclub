package io.namoosori.tc.entity.club;

import io.namoosori.tc.entity.BaseTimeEntity;
import io.namoosori.tc.entity.vo.Address;
import io.namoosori.tc.shared.NameValue;
import io.namoosori.tc.shared.NameValueList;
import io.namoosori.tc.utility.InvalidEmailException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "COMMUNITY_MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Proxy(lazy = false)
public class CommunityMember extends BaseTimeEntity {
    //
    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long memberId;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String name;

    @Column
    private String nickName;

    @Column
    private String phoneNumber;

    @Column
    private String birthDay;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "member")
    private List<ClubMembership> clubMemberships = new ArrayList<>();

    @OneToMany(mappedBy = "communityMember")
    private List<Posting> postings = new ArrayList<>();


    public List<ClubMembership> getMembershipList() {
        return clubMemberships;
    }

    public void setMembershipList(List<ClubMembership> membershipList) {
        this.clubMemberships = membershipList;
    }
}
