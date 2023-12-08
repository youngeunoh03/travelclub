package io.namoosori.tc.store.repository;

import io.namoosori.tc.entity.club.TravelClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<TravelClub, String> {
    //
    List<TravelClub> findAllByClubName(String clubName);
}
