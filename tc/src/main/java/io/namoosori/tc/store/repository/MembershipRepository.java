package io.namoosori.tc.store.repository;

import io.namoosori.tc.entity.club.ClubMembership;
import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.dto.MembershipDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<ClubMembership, String> {
    //
    boolean existsByMemberAndClub(CommunityMember member, TravelClub club);
    List<ClubMembership> findByClub_ClubId(Long clubId);
    List<ClubMembership> findByMember_MemberId(Long memberId);
}
