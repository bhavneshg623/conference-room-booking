package ae.mashrebank.conferenceroom.booking.resource;

import ae.mashrebank.conferenceroom.booking.dto.AvailableRoomResponse;
import ae.mashrebank.conferenceroom.booking.dto.RoomBookingRequest;
import ae.mashrebank.conferenceroom.booking.dto.RoomBookingResponse;
import ae.mashrebank.conferenceroom.booking.service.ConferenceRoomService;
import ae.mashrebank.conferenceroom.booking.validator.BookingRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1")
@Slf4j
@RequiredArgsConstructor
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    private final BookingRequestValidator requestValidator;

    @GetMapping(
            value = "/conferencerooms",
            produces = {"application/json; charset=utf-8", "application/json", "application/jose+jwe"}
    )
    public ResponseEntity<AvailableRoomResponse> availableRooms(
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime){
        log.info("request received to fetch available meeting room for startTime: {}, endTime:{}",startTime,endTime);
        requestValidator.validateTime(startTime,endTime);

        return ResponseEntity.ok(conferenceRoomService.findAvailableRoom(startTime,endTime));
    }


    @PostMapping(
            value = "/conferenceroom/book",
            produces = {"application/json; charset=utf-8", "application/json", "application/jose+jwe"}
    )
    public ResponseEntity<RoomBookingResponse> bookRoom(@RequestBody RoomBookingRequest roomBookingRequest){

        log.info("request received to book meeting room for request {}",roomBookingRequest);
        requestValidator.validate(roomBookingRequest);

        return ResponseEntity.ok(conferenceRoomService.bookRoom(roomBookingRequest));
    }



}
