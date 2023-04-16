package ae.mashrebank.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableRoomResponse {

    @JsonProperty("availableRooms")
    private List<Room> rooms;

}
