package org.app.service;

import org.app.paylaod.request.AuthenticationReqDTO;
import org.app.paylaod.request.RegistrationReqPayload;
import org.app.paylaod.response.AuthenticationResDTO;
import org.app.paylaod.response.RegistrationResPayload;

/**
 * Interface for managing user registration and confirmation processes.
 */
public interface IRegistrationService {
    RegistrationResPayload registration(RegistrationReqPayload request);

    String confirmToken(String token);

     AuthenticationResDTO authenticateUser(AuthenticationReqDTO authenticationReqDTO);
}
