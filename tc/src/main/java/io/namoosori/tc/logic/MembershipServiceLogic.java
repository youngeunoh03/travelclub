package io.namoosori.tc.logic;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.entity.vo.RoleInClub;
import io.namoosori.tc.service.MembershipService;
import io.namoosori.tc.service.dto.MembershipDto;
import io.namoosori.tc.shared.NameValueList;
import io.namoosori.tc.store.repository.ClubRepository;
import io.namoosori.tc.store.repository.MemberRepository;
import io.namoosori.tc.store.repository.MembershipRepository;
import io.namoosori.tc.utility.NoSuchClubException;
import io.namoosori.tc.utility.NoSuchMemberException;
import io.namoosori.tc.utility.NoSuchMembershipException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipServiceLogic implements MembershipService {
    private MembershipRepository membershipRepository;
    private ClubRepository clubRepository;
    private MemberRepository memberRepository;

    public MembershipServiceLogic(MembershipRepository membershipRepository, ClubRepository clubRepository, MemberRepository memberRepository) {
        this.membershipRepository = membershipRepository;
        this.clubRepository = clubRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public String registerMembership(MembershipDto membershipDto) {
        Long memberId = membershipDto.getMemberId();
        String id = String.valueOf(memberId);
        String clubId = String.valueOf(membershipDto.getClubId());

        CommunityMember member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException("No such member exists."));

        TravelClub club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchClubException("No such club exists."));

        boolean doesMembershipExist = membershipRepository.existsByMemberAndClub(member, club);

        if (doesMembershipExist) {
            return "Membership already exists.";
        }

        ClubMembership newMembership = new ClubMembership();
        newMembership.setMember(member);
        newMembership.setClub(club);

        membershipRepository.save(newMembership);

        return "Membership registered.";
    }

    @Override
    public ClubMembership findMembership(Long membershipId) {
        String id = String.valueOf(membershipId);
        Optional<ClubMembership> membership = membershipRepository.findById(id);
        return membership.orElse(null);
    }

    @Override
    public List<ClubMembership> findAllMembershipsOfClub(Long clubId) {
        return membershipRepository.findByClub_ClubId(clubId);
    }

    @Override
    public List<ClubMembership> findAllMembershipsOfMember(Long memberId) {
//        String id = String.valueOf(memberId);
        return membershipRepository.findByMember_MemberId(memberId);
    }

    @Override
    public ClubMembership modifyMembership(String membershipId, MembershipDto membershipDto) {
        ClubMembership targetMembership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new NoSuchMembershipException("Membership does not exist."));

        if (membershipDto.getMemberEmail() != null) {
            targetMembership.setMemberEmail(membershipDto.getMemberEmail());
        }
        if (membershipDto.getRole() != null) {
            targetMembership.setRole(membershipDto.getRole());
        }

        membershipRepository.save(targetMembership);
        return targetMembership;
    }

    @Override
    public void removeMembership(String membershipId) {
        ClubMembership targetMembership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new NoSuchMembershipException("Membership does not exist."));

        membershipRepository.delete(targetMembership);
    }
    //

}
