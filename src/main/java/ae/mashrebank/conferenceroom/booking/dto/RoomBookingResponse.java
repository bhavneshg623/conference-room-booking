package ae.mashrebank.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomBookingResponse {

    @JsonProperty("room")
    private Room room;

    @JsonProperty("startTime")
    private Time startTime;

    @JsonProperty("endTime")
    private Time endTime;

    @JsonProperty("bookingStatus")
    private String status;
}
