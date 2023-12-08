package io.namoosori.tc.service;

import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.service.dto.MemberDto;
import io.namoosori.tc.service.dto.MembershipDto;
import io.namoosori.tc.shared.NameValueList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    //
    public CommunityMember registerMember(CommunityMember member);
    CommunityMember findMemberById(Long memberId);
    CommunityMember findMemberByEmail(String email);
    List<CommunityMember> findMembersByName(String name);
    public CommunityMember modifyMember(Long memberId, MemberDto memberDto);
    void removeMember(Long memberId);

    List<MembershipDto> findAllMembershipsIn(String email);
    void removeMembership(Long clubId, String email);
}