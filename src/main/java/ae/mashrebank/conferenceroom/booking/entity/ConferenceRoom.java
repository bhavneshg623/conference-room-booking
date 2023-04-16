package ae.mashrebank.conferenceroom.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "conference_room")
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ConferenceRoom implements Comparable<ConferenceRoom> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name= "capacity")
    private int capacity;

    @OneToMany(mappedBy = "conferenceRoom")
    private Collection<Booking> bookings = new ArrayList<>();

    public ConferenceRoom(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int compareTo(ConferenceRoom o) {
        return this.capacity - o.getCapacity();
    }
}
