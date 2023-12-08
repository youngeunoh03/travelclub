package io.namoosori.tc.entity.club;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import io.namoosori.tc.entity.BaseTimeEntity;
import io.namoosori.tc.service.dto.BoardDto;
import io.namoosori.tc.util.DateUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Setter
@Getter
@Table(name = "SOCIAL_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class SocialBoard extends BaseTimeEntity {
    //
    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long boardId;

    @Column
    @NotNull
    private String boardName;

    @Column
    @NotNull
    private String adminEmail;

    @OneToOne
    @JoinColumn(name = "clubId")
    private TravelClub travelClub;

    @OneToMany(mappedBy = "socialBoard", cascade = CascadeType.ALL)
    private List<Posting> postings = new ArrayList<>();

    public BoardDto EntityToDto() {
        return BoardDto.builder()
                .boardId(this.boardId)
                .boardName(this.boardName)
                .clubId(this.travelClub.getClubId())
                .build();
    }

}

