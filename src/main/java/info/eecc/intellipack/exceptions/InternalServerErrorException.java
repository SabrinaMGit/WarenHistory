package info.eecc.intellipack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// convenient and Rcycle specific version of ResponseStatusException
public class InternalServerErrorException extends ResponseStatusException {

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, null, cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}
