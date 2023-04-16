package ae.mashrebank.conferenceroom.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @JsonProperty("name")
    private String name;

    @JsonProperty("capacity")
    private int capacity;
}
