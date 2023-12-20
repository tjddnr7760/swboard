package swempire.server.global.auth.memberDetails;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberContextInform {

    private final Long id;
    private final String email;
    private final String name;

    @Builder
    public MemberContextInform(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
