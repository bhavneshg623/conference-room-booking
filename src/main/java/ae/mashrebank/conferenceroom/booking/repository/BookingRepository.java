package ae.mashrebank.conferenceroom.booking.repository;

import ae.mashrebank.conferenceroom.booking.entity.Booking;
import ae.mashrebank.conferenceroom.booking.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT new ae.mashrebank.conferenceroom.booking.entity.ConferenceRoom(c.id,c.name,c.capacity) " +
            "FROM Booking b , ConferenceRoom c " +
            "WHERE " +
            "b.startTime < ?2 AND b.endTime > ?1 " +
            "AND b.conferenceRoom = c.id")
    ArrayList<ConferenceRoom> findBookedRoom(Time startTime, Time endTime);
}
