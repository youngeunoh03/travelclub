package io.namoosori.tc.logic;

import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.Posting;
import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.PostingService;
import io.namoosori.tc.service.dto.PostingDto;
import io.namoosori.tc.service.dto.TravelClubDto;
import io.namoosori.tc.store.repository.BoardRepository;
import io.namoosori.tc.store.repository.ClubRepository;
import io.namoosori.tc.store.repository.MemberRepository;
import io.namoosori.tc.store.repository.PostingRepository;
import io.namoosori.tc.utility.NoSuchBoardException;
import io.namoosori.tc.utility.NoSuchClubException;
import io.namoosori.tc.utility.NoSuchMemberException;
import io.namoosori.tc.utility.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostingServiceLogic implements PostingService {
    //
    private PostingRepository postingRepository;
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    public PostingServiceLogic(PostingRepository postingRepository, BoardRepository boardRepository,
                               MemberRepository memberRepository) {
        this.postingRepository = postingRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public String register(PostingDto postingDto) {

        Optional<SocialBoard> targetBoard = Optional.ofNullable(boardRepository.findById(String.valueOf(postingDto.getBoardId())))
                .orElseThrow(() -> new NoSuchBoardException("Board does not exist."));

        Optional<CommunityMember> targetMember = Optional.ofNullable(memberRepository.findById(String.valueOf(postingDto.getMemberId())))
                .orElseThrow(() -> new NoSuchMemberException("Member does not exist."));

        Posting newPosting = new Posting();
        newPosting.setTitle(postingDto.getTitle());
        newPosting.setContents(postingDto.getContents());
        newPosting.setSocialBoard(targetBoard.orElse(null));
        newPosting.setCommunityMember(targetMember.orElse(null));

        Posting savedPost = postingRepository.save(newPosting);
        return "Posting has been uploaded.";
    }

    @Override
    public Posting find(Long postingId) {
        Optional<Posting> targetPost = Optional.ofNullable(postingRepository.findById(String.valueOf(postingId)))
                .orElseThrow(() -> new NoSuchPostingException("Post does not exist."));

        return targetPost.get();
    }

    @Override
    public List<PostingDto> findByBoardId(Long boardId) {
//        Optional<SocialBoard> targetBoard = Optional.ofNullable(boardRepository.findById(String.valueOf(boardId))
//                .orElseThrow(() -> new NoSuchBoardException("Board does not exist.")));
//
        return postingRepository.findByBoardId(boardId).stream()
                .map(posting -> new PostingDto(posting)).collect(Collectors.toList());
    }

    @Override
    public void modify(Long postingId, PostingDto postingDto) {
        Posting targetPosting = postingRepository.findById(String.valueOf(postingId))
                .orElseThrow(() -> new NoSuchPostingException("Post does not exist."));

        if (postingDto.getTitle() != null) {
            targetPosting.setTitle(postingDto.getTitle());
        }
        if (postingDto.getContents() != null) {
            targetPosting.setContents(postingDto.getContents());
        }

        postingRepository.save(targetPosting);
    }

    @Override
    public String remove(Long postingId) {
        Posting targetPosting = postingRepository.findById(String.valueOf(postingId))
                .orElseThrow(() -> new NoSuchPostingException("Post does not exist."));

        postingRepository.delete(targetPosting);
        return "Posting has been removed.";
    }

    @Override
    public void removeAllIn(Long boardId) {
        postingRepository.findByBoardId(boardId).stream()
                .forEach(posting -> postingRepository.delete(posting));
    }
}
