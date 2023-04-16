package ae.mashrebank.conferenceroom.booking.validator;

import ae.mashrebank.conferenceroom.booking.dto.RoomBookingRequest;
import ae.mashrebank.conferenceroom.booking.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.BAD_BOOKING_REQUEST;
import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.BAD_CAPACITY_VALUE;
import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.BAD_TIME_FORMAT;
import static java.util.Objects.isNull;


/**
 * Request Validator
 */

@Component
@Slf4j
public class BookingRequestValidator {

    /**
     * Validates time String
     */
    public void validate(RoomBookingRequest request) {

        log.info("request validation started");
        if(!isNull(request)){
            validateTime(request.getStartTime(),request.getEndTime());
            validateCapacity(request.getCapacity());
        }
        else{
            throw new BaseException(BAD_BOOKING_REQUEST, HttpStatus.BAD_REQUEST);
        }
        log.info("request validation completed");
    }

    public void validateCapacity(int capacity){
        if (capacity <= 0 ) {
            throw new BaseException(BAD_CAPACITY_VALUE, HttpStatus.BAD_REQUEST);
        }
        log.info("capacity validated successfully");
    }

    public void validateTime(String StartTime, String EndTime){
        if ((StartTime.isEmpty() || !StartTime.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))
                && (EndTime.isEmpty() || !EndTime.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) ) {
            throw new BaseException(BAD_TIME_FORMAT, HttpStatus.BAD_REQUEST);
        }
        log.info("startTime and endTime validated successfully");
    }
}
