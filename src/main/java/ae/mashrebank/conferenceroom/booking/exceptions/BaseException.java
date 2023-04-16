package ae.mashrebank.conferenceroom.booking.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base Exception class
 */
@Getter
public class BaseException extends RuntimeException{

    private String message;

    private String code;

    private HttpStatus status;

    /**
     * Constructor with all the 3 params
     * @param message
     * @param status
     */
    public BaseException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public BaseException(String message) {
        this.message = message;
    }

}
