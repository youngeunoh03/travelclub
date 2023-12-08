package io.namoosori.tc.entity.club;

import io.namoosori.tc.entity.BaseTimeEntity;
import io.namoosori.tc.service.dto.PostingDto;
import io.namoosori.tc.util.DateUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Posting")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Posting extends BaseTimeEntity {
    //
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ_GENERATOR")
    private long postingId;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String contents;

    @Column
    private Long boardId;

//    @Column
//    @NotNull
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    private String writtenDate;

//    @Column
//    @NotNull
//    private int readCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSTINGS_BOARD")
    private SocialBoard socialBoard;

    @ManyToOne
    @JoinColumn(name = "POSTINGS_MEMBER")
    private CommunityMember communityMember;

    public PostingDto EntityToDto() {
        return PostingDto.builder()
                .postingId(this.postingId)
                .title(this.title)
                .contents(this.contents)
                .memberId(this.communityMember.getMemberId())
                .boardId(this.socialBoard.getBoardId())
                .build();
    }

//    public Long getSocialBoardId() {
//        Long id = socialBoard.getBoardId();
//        return id;
//    }
}
