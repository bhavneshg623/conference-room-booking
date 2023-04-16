package ae.mashrebank.conferenceroom.booking.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionAdvice for handling custom and other exceptions to return generic error
 */
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * Handling BaseException which are derived as customer exceptions.
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .status(ex.getStatus())
                .build(),
                ex.getStatus());
    }
}
