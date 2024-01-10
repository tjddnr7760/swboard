package swempire.server.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import swempire.server.domain.board.domain.Board;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private Boolean twoFactor = false;

    @Builder
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void encodeMemberPassword(String encodePassword) {
        this.password = encodePassword;
    }

    public void twoFactorTrue() {
        this.twoFactor = true;
    }

    public void twoFactorFalse() {
        this.twoFactor = false;
    }
}
