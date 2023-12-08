package io.namoosori.tc.service;

import io.namoosori.tc.entity.club.Posting;
import io.namoosori.tc.service.dto.PostingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostingService {
    String register(PostingDto postingDto);
    Posting find(Long postingId);
    List<PostingDto> findByBoardId(Long boardId);
    void modify(Long postingId, PostingDto postingDto);
    String remove(Long postingId);
    void removeAllIn(Long boardId);
}
