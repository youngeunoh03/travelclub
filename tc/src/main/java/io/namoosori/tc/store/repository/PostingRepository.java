package io.namoosori.tc.store.repository;

import io.namoosori.tc.entity.club.Posting;
import io.namoosori.tc.service.dto.PostingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, String> {
    //
    public List<Posting> findByBoardId(Long boardId);
}
