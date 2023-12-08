package io.namoosori.tc.service;

import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.service.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    SocialBoard register(BoardDto boardDto);
    List<SocialBoard> findById(Long boardId);
//    BoardDto findByClubName(String clubName);
    SocialBoard modify(Long boardId, BoardDto boardDto);
    void remove(Long boardId);
}
