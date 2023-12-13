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
        Member member = new Member();

    }
}
