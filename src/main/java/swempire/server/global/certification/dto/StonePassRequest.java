package swempire.server.global.certification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StonePassRequest {

    private final String email;

    private final String pinCode;

    @Builder
    public StonePassRequest(String email, String pinCode) {
        this.email = email;
        this.pinCode = pinCode;
    }
}
