package io.namoosori.tc.store.repository;

import io.namoosori.tc.entity.club.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<CommunityMember, String> {
    //
    public CommunityMember findByEmail(String email);
    public List<CommunityMember> findByName(String name);
}
