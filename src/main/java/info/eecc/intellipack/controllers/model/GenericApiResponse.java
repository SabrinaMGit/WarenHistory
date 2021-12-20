package info.eecc.intellipack.controllers.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */

@Builder
@NoArgsConstructor
@Data
public class GenericApiResponse {

    private String message;
    private String path;
    private String reason;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public GenericApiResponse(String message, String path, String reason, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.path = path;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    private static GenericApiResponse buildGenericApiResponse(HttpStatus status, String message, String path, String reason, LocalDateTime timestamp) {
        return new GenericApiResponse(message, path, reason, status, timestamp);
    }

    public static ResponseEntity<GenericApiResponse> buildResponse(HttpStatus status, String message, String path, String reason, LocalDateTime timestamp) {
        return new ResponseEntity<>(buildGenericApiResponse(status, message, path, reason, timestamp), status);
    }
}
