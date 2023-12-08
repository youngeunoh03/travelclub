package io.namoosori.tc.service.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    //
    private Long memberId;
    private String memberEmail;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String birthDay;

    public MemberDto(String memberEmail, String name, String nickName, String phoneNumber, String birthDay) {
        this.memberEmail = memberEmail;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
    }
}
