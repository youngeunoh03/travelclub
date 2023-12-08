package io.namoosori.tc.store.repository;

import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.service.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<SocialBoard, String> {
    //
//    public BoardDto findByClubName(String clubName);
}
