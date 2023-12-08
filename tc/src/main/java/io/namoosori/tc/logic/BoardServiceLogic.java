package io.namoosori.tc.logic;

import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.service.BoardService;
import io.namoosori.tc.service.dto.BoardDto;
import io.namoosori.tc.store.repository.BoardRepository;
import io.namoosori.tc.store.repository.ClubRepository;
import io.namoosori.tc.util.StringUtil;
import io.namoosori.tc.utility.NoSuchBoardException;
import io.namoosori.tc.utility.NoSuchClubException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceLogic implements BoardService {
    private final BoardRepository boardRepository;
    private final ClubRepository clubRepository;

    public BoardServiceLogic(BoardRepository boardRepository, ClubRepository clubRepository) {
        super();
        this.boardRepository = boardRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public SocialBoard register(BoardDto boardDto) {
        String boardId = String.valueOf(boardDto.getClubId());
        Optional<TravelClub> optionalClub = clubRepository.findById(boardId);

        if (optionalClub.isPresent()) {
            TravelClub club = optionalClub.get();
        } else {
            throw new NoSuchClubException("Club does not exist.");
        }

        SocialBoard registeredBoard = boardRepository.save(new SocialBoard());
        return registeredBoard;
    }

    @Override
    public List<SocialBoard> findById(Long boardId) {
        String id = String.valueOf(boardId);
        Optional<SocialBoard> boards = boardRepository.findById(id);

        if (boards.isPresent()) {
            return boards.stream().collect(Collectors.toList());
        } else {
            throw new NoSuchClubException("No board with such name exists.");
        }
    }

//    @Override
//    public BoardDto findByClubName(String clubName) {
//        return boardRepository.findByClubName(clubName);
//    }

    @Override
    @SuppressWarnings("ALL")
    public SocialBoard modify(Long boardId, BoardDto boardDto) {
        Optional<SocialBoard> targetBoard = boardRepository.findById(String.valueOf(boardId));

        SocialBoard board = targetBoard.orElse(null);

        if (StringUtil.isEmpty(boardDto.getBoardName())) {
            boardDto.setBoardName(board.getBoardName());
        }

        if (StringUtil.isEmpty(boardDto.getAdminEmail())) {
            boardDto.setAdminEmail(board.getAdminEmail());
        }

        return boardRepository.save(board);
    }

    @Override
    public void remove(Long boardId) {
        String id = String.valueOf(boardId);
        boardRepository.deleteById(id);
    }
}
