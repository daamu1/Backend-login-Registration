package org.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Registration {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
