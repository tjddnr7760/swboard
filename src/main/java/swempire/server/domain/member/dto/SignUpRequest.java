package swempire.server.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import swempire.server.domain.member.domain.Member;

@Getter
public class SignUpRequest {

    private final String email;

    private final String password;

    private final String name;

    @Builder
    public SignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member toMember() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .build();
    }
}
