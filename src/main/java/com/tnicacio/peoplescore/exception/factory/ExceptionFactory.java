package com.tnicacio.peoplescore.exception.factory;

import com.tnicacio.peoplescore.exception.DatabaseException;
import com.tnicacio.peoplescore.exception.EventException;
import com.tnicacio.peoplescore.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionFactory {

    public RuntimeException database(String message) {
        return new DatabaseException(message);
    }

    public RuntimeException notFound(String message) {
        return new ResourceNotFoundException(message);
    }

    public RuntimeException event(String message, Throwable throwable) {
        return new EventException(message, throwable);
    }

    public UsernameNotFoundException usernameNotFound(String message) {
        return new UsernameNotFoundException(message);
    }
}
