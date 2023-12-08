package io.namoosori.tc.service.dto;

import io.namoosori.tc.entity.club.CommunityMember;
import io.namoosori.tc.entity.club.Posting;
import io.namoosori.tc.entity.club.SocialBoard;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostingDto implements Serializable {
    //
    private Long postingId;
    private String title;
    private Long memberId;
    private Long boardId;
    private String contents;
    private LocalDateTime createdDate;

    public PostingDto(Posting posting) {
        this.title = posting.getTitle();
        this.memberId = posting.getCommunityMember().getMemberId();
        this.boardId = posting.getSocialBoard().getBoardId();
        this.contents = posting.getContents();
        this.createdDate = posting.getCreatedDate();
    }

    public Posting DtoToEntity() {
        return Posting.builder()
                .postingId(this.postingId)
                .title(this.title)
                .contents(this.contents)
                .socialBoard(SocialBoard.builder()
                        .boardId(this.boardId)
                        .build())
                .communityMember(CommunityMember.builder()
                        .memberId(this.memberId)
                        .build())
                .build();
    }
}
