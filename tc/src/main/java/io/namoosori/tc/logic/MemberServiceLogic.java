package io.namoosori.tc.logic;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.MemberService;
import io.namoosori.tc.service.dto.MemberDto;
import io.namoosori.tc.service.dto.MembershipDto;
import io.namoosori.tc.shared.NameValueList;
import io.namoosori.tc.store.repository.ClubRepository;
import io.namoosori.tc.store.repository.MemberRepository;
import io.namoosori.tc.store.repository.MembershipRepository;
import io.namoosori.tc.util.StringUtil;
import io.namoosori.tc.utility.NoSuchMemberException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceLogic implements MemberService {
    //
    private MemberRepository memberRepository;
    private ClubRepository clubRepository;

    public MemberServiceLogic(MemberRepository memberRepository) {
        super();
        this.memberRepository = memberRepository;
    }

    @Override
    public CommunityMember registerMember(CommunityMember member) {
        return memberRepository.save(member);
    }

    @Override
    public CommunityMember findMemberById(Long memberId) {
        String id = String.valueOf(memberId);
        Optional<CommunityMember> foundMember = memberRepository.findById(id);

        if (foundMember.isEmpty()) {
            throw new NoSuchMemberException("No such member exists.");
        }

        CommunityMember member = foundMember.orElse(null);

        return member;
    }

    @Override
    public CommunityMember findMemberByEmail(String email) {
        CommunityMember foundMember = memberRepository.findByEmail(email);

        if (foundMember.equals(null)) {
            throw new NoSuchMemberException("No such member exists.");
        }

        return foundMember;
    }

    @Override
    public List<CommunityMember> findMembersByName(String name) {
        List<CommunityMember> foundMember = memberRepository.findByName(name);

        if (foundMember.equals(null)) {
            throw new NoSuchMemberException("No such member exists.");
        }

        return foundMember;
    }

    @Override
    public CommunityMember modifyMember(Long memberId, MemberDto memberDto) {
        String id = String.valueOf(memberId);
        CommunityMember targetMember = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException("No such member exists."));

        if (memberDto.getMemberEmail() != null) {
            targetMember.setEmail(memberDto.getMemberEmail());
        }
        if (memberDto.getName() != null) {
            targetMember.setName(memberDto.getName());
        }
        if (memberDto.getNickName() != null) {
            targetMember.setNickName(memberDto.getNickName());
        }
        if (memberDto.getPhoneNumber() != null) {
            targetMember.setPhoneNumber(memberDto.getPhoneNumber());
        }
        if (memberDto.getBirthDay() != null) {
            targetMember.setBirthDay(memberDto.getBirthDay());
        }
//        targetMember.setEmail(memberDto.getMemberEmail());
//        targetMember.setName(memberDto.getName());
//        targetMember.setNickName(memberDto.getNickName());
//        targetMember.setPhoneNumber(memberDto.getPhoneNumber());
//        targetMember.setBirthDay(memberDto.getBirthDay());
//
        return memberRepository.save(targetMember);
    }

    @Override
    public void removeMember(Long memberId) {
        String id = String.valueOf(memberId);

        memberRepository.deleteById(id);
    }

    @Override
    public List<MembershipDto> findAllMembershipsIn(String email) {
        CommunityMember member = findMemberByEmail(email);

        return member.getMembershipList().stream().map(membership -> new MembershipDto(membership))
                .collect(Collectors.toList());
    }

    public void removeMembership(Long clubId, String email) {
        Optional<TravelClub> foundClub = clubRepository.findById(String.valueOf(clubId));
        CommunityMember foundMember = memberRepository.findByEmail(email);

        TravelClub club = foundClub.orElse(null);

        ClubMembership clubMembership = getMembershipsIn(club, email);

        club.getMembershipList().remove(clubMembership);
        clubRepository.save(club);
        foundMember.getMembershipList().remove(clubMembership);
        memberRepository.save(foundMember);
    }

    private ClubMembership getMembershipsIn(TravelClub club, String memberEmail) {
        //
        for (ClubMembership membership : club.getMemberships()) {
            if (memberEmail.equals(membership.getMemberEmail())) {
                return membership;
            }
        }
        throw new NoSuchMemberException("No such member exists with email.");
    }
}
