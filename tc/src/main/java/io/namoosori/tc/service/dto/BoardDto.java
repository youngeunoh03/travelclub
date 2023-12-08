package io.namoosori.tc.service.dto;

import io.namoosori.tc.entity.club.SocialBoard;
import io.namoosori.tc.entity.club.TravelClub;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto implements Serializable {
    //
    private long boardId;
    private long clubId;
    private String boardName;
    private String adminEmail;

    public BoardDto(SocialBoard board) {
        this.clubId = board.getTravelClub().getClubId();
        this.boardName = board.getBoardName();
        this.adminEmail = board.getAdminEmail();
    }

    public SocialBoard DtoToEntity() {
        return SocialBoard.builder()
                .boardId(this.boardId)
                .boardName(this.boardName)
                .adminEmail(this.adminEmail)
                .travelClub(TravelClub.builder()
                        .clubId(this.clubId)
                        .build())
                .build();
    }
}
