package ae.mashrebank.conferenceroom.booking.repository;

import ae.mashrebank.conferenceroom.booking.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;

@Repository
public interface RoomRepository extends JpaRepository<ConferenceRoom, Integer> {

    ArrayList<ConferenceRoom> findByIdNotIn(HashSet<Integer> bookedRoomIds);
}
