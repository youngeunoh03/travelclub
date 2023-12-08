package io.namoosori.tc.service;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.service.dto.MembershipDto;
import org.modelmapper.internal.util.Members;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MembershipService {
    //
    String registerMembership(MembershipDto membershipDto);
    ClubMembership findMembership(Long membershipId);
    List<ClubMembership> findAllMembershipsOfClub(Long clubId);
    List<ClubMembership> findAllMembershipsOfMember(Long memberId);
    ClubMembership modifyMembership(String membershipId, MembershipDto membershipDto);
    void removeMembership(String membershipId);
}

