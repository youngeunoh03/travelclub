package io.namoosori.tc.controller;

import io.namoosori.tc.entity.club.Posting;
import io.namoosori.tc.service.BoardService;
import io.namoosori.tc.service.MemberService;
import io.namoosori.tc.service.PostingService;
import io.namoosori.tc.service.dto.PostingDto;
import io.namoosori.tc.utility.NoSuchBoardException;
import io.namoosori.tc.utility.NoSuchPostingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/posting")
@RestController
public class PostingController {
    @Autowired
    private ModelMapper modelMapper;

    private MemberService memberService;
    private PostingService postingService;
    private BoardService boardService;

    public PostingController(MemberService memberService, PostingService postingService, BoardService boardService) {
        super();
        this.memberService = memberService;
        this.postingService = postingService;
        this.boardService = boardService;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping
    public ResponseEntity<PostingDto> register(@RequestBody PostingDto postingDto) {
        Optional.ofNullable(boardService.findById(postingDto.getBoardId()))
                .orElseThrow(() -> new NoSuchBoardException("No such board exists."));

        postingService.register(postingDto);
        return new ResponseEntity<PostingDto>(postingDto, HttpStatus.CREATED);
    }

    @GetMapping("/find/{postingId}")
    public PostingDto find(@PathVariable(name = "postingId") Long postingId) {
        Posting posting = postingService.find(postingId);
        if (posting.equals(null)) {
            throw new NoSuchPostingException("Posting does not exist.");
        }

        PostingDto postingResponse = modelMapper.map(posting, PostingDto.class);
        return postingResponse;
    }

    @GetMapping("/findall/{boardId}")
    public List<PostingDto> findByBoardId(@PathVariable(name = "boardId") Long boardId) {
        return postingService.findByBoardId(boardId).stream()
                .map(postings -> modelMapper.map(postings, PostingDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{postingId}")
    public String modify(@PathVariable(name = "postingId") Long postingId, @RequestBody PostingDto postingDto) {
        postingService.modify(postingId, postingDto);
        return "Posting has been successfully modified.";
    }

    @DeleteMapping("/delete/{postingId}")
    public String delete(@PathVariable(name = "postingId") Long postingId) {
        postingService.remove(postingId);
        return "Posting has been removed.";
    }

    @DeleteMapping("/deleteAll/{boardId}")
    public String deleteAll(@PathVariable(name = "boardId") Long boardId) {
        postingService.removeAllIn(boardId);
        return "All posting has been deleted.";
    }


}
