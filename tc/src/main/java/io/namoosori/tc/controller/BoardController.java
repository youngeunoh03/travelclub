package io.namoosori.tc.controller;

import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.service.BoardService;
import io.namoosori.tc.service.dto.BoardDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/club/board")
public class BoardController {
    @Autowired
    private ModelMapper modelMapper;

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        super();
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardDto> register(@RequestBody BoardDto boardDto) {
        BoardDto boardRequest = modelMapper.map(boardDto, BoardDto.class);

        SocialBoard board = boardService.register(boardRequest);

        BoardDto boardResponse = modelMapper.map(board, BoardDto.class);

        return new ResponseEntity<BoardDto>(boardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public List<SocialBoard> findBoardById(@PathVariable(name = "boardId") Long boardId) {
        return boardService.findById(boardId)
                .stream().map(boards -> modelMapper.map(boards, SocialBoard.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable(name = "boardId") long boardId, @RequestBody BoardDto boardDto) {
        SocialBoard boardRequest = modelMapper.map(boardDto, SocialBoard.class);

        SocialBoard board = boardService.modify(boardId, boardDto);

        BoardDto boardResponse = modelMapper.map(board, BoardDto.class);

        return ResponseEntity.ok().body(boardResponse);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable(name = "boardId") long boardId) {
        boardService.remove(boardId);
    }

}
