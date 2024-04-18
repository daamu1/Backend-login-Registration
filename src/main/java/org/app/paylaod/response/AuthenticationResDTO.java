package org.app.paylaod.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResDTO {
    String userName;
    String email;
    String accessToken;
}
