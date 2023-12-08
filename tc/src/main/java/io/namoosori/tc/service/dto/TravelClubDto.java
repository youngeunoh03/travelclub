package io.namoosori.tc.service.dto;

import io.namoosori.tc.entity.club.TravelClub;
import io.namoosori.tc.util.DateUtil;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelClubDto implements Serializable {
    //
    private long clubId;
    private String clubName;
    private String clubIntro;
    private LocalDateTime createdDate;

    public TravelClubDto(TravelClub travelClub) {
        //
        this.clubId = travelClub.getClubId();
        this.clubName = travelClub.getClubName();
        this.clubIntro = travelClub.getClubIntro();
        this.createdDate = travelClub.getCreatedDate();
    }

    public TravelClub DtoToEntity() {
        return TravelClub.builder()
                .clubId(this.clubId)
                .clubName(this.clubName)
                .clubIntro(this.clubIntro)
                .build();
    }
}
