package swempire.server.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import swempire.server.domain.member.domain.Member;

@Builder
@Getter
public class SignUpDto {

    private String email;

    private String password;

    private String name;

    public Member toMember() {
        return Member.builder()
                .email("tjddnr7760@naver.com")
                .build();
    }
}
