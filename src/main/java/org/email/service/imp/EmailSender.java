package org.email.service.imp;

public interface EmailSender {
    void send(String to,String email) throws IllegalAccessException;
}
