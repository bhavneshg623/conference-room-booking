package ae.mashrebank.conferenceroom.booking.repository;

import ae.mashrebank.conferenceroom.booking.entity.MaintenanceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceDetails, Integer> {

    @Query(value = "SELECT m  " +
            "FROM MaintenanceDetails m " +
            "WHERE " +
            "m.startTime < ?2 AND m.endTime > ?1 ")
    ArrayList<MaintenanceDetails> findMaintenanceSlot(Time startTime, Time endTime);
}
