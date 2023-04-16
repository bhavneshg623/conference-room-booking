package ae.mashrebank.conferenceroom.booking.service;

import ae.mashrebank.conferenceroom.booking.dto.AvailableRoomResponse;
import ae.mashrebank.conferenceroom.booking.dto.RoomBookingRequest;
import ae.mashrebank.conferenceroom.booking.dto.RoomBookingResponse;
import ae.mashrebank.conferenceroom.booking.entity.Booking;
import ae.mashrebank.conferenceroom.booking.entity.ConferenceRoom;
import ae.mashrebank.conferenceroom.booking.entity.MaintenanceDetails;
import ae.mashrebank.conferenceroom.booking.exceptions.BaseException;
import ae.mashrebank.conferenceroom.booking.mapper.ConferenceRoomBookingMapper;
import ae.mashrebank.conferenceroom.booking.repository.BookingRepository;
import ae.mashrebank.conferenceroom.booking.repository.MaintenanceRepository;
import ae.mashrebank.conferenceroom.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.AVAILABILITY_NOT_FOUND;
import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.BAD_TIME_FORMAT;
import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.MAINTENANCE_CONFLICT;
import static ae.mashrebank.conferenceroom.booking.constants.ErrorMessageConstants.SUITABILITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConferenceRoomService {

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    private final MaintenanceRepository maintenanceRepository;

    private static final String TIME_SLOT_FORMAT = "HH:mm";

   public AvailableRoomResponse findAvailableRoom(String startTimeString, String endTimeSring){
       Time startTime = covertStringToTime(startTimeString);
       Time endTime = covertStringToTime(endTimeSring);

       List<MaintenanceDetails> maintenanceDetails = findMaintenanceSlotForRange(startTime,endTime);
       if(!maintenanceDetails.isEmpty()){
           throw new BaseException(MAINTENANCE_CONFLICT, HttpStatus.CONFLICT);
       }

       List<ConferenceRoom> bookedConferenceRooms = findBookedRoomsForRange(startTime, endTime);

       List<ConferenceRoom> roomEntities = findAvailableRooms(bookedConferenceRooms);
       if(roomEntities.isEmpty()){
           throw new BaseException(AVAILABILITY_NOT_FOUND, HttpStatus.NOT_FOUND);
       }

       return ConferenceRoomBookingMapper.INSTANCE.mapToAvailableRoomResponse(0,roomEntities);
   }


    public RoomBookingResponse bookRoom(RoomBookingRequest roomBookingRequest){

        Time startTime = covertStringToTime(roomBookingRequest.getStartTime());
        Time endTime = covertStringToTime(roomBookingRequest.getEndTime());

        List<MaintenanceDetails> maintenanceDetails = findMaintenanceSlotForRange(startTime,endTime);

        if(!maintenanceDetails.isEmpty()){
            throw new BaseException(MAINTENANCE_CONFLICT, HttpStatus.CONFLICT);
        }

        List<ConferenceRoom> bookedConferenceRooms = findBookedRoomsForRange(startTime, endTime);

        List<ConferenceRoom> roomEntities = findAvailableRooms(bookedConferenceRooms);

        if(roomEntities.isEmpty()){
            throw new BaseException(AVAILABILITY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Collections.sort(roomEntities);

        Optional<ConferenceRoom> suitableRoomOptional = findSuitableRoomByCapacity(roomEntities,roomBookingRequest.getCapacity());

        Booking bookingEntity;
        if(suitableRoomOptional.isEmpty()){
            throw new BaseException(SUITABILITY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else{
             Booking booking = Booking.builder().startTime(startTime)
                .endTime(endTime)
                .conferenceRoom(suitableRoomOptional.get())
                .build();

            bookingEntity = bookingRepository.save(booking);
            bookingRepository.flush();
        }

        return ConferenceRoomBookingMapper.INSTANCE.mapToRoomBookingResponse(bookingEntity);


    }

    private Optional<ConferenceRoom> findSuitableRoomByCapacity(List<ConferenceRoom> roomEntities, int requiredCapacity){
        Optional<ConferenceRoom> suitableRoomOptional = roomEntities.stream().filter(r-> r.getCapacity()>=requiredCapacity).findFirst();
        return suitableRoomOptional;
    }

    private List<ConferenceRoom> findAvailableRooms(List<ConferenceRoom> bookedConferenceRooms){
        List<ConferenceRoom> roomEntities;
        if(!bookedConferenceRooms.isEmpty()) {
            HashSet<Integer> bookedRoomIds = new HashSet<>();

            for (ConferenceRoom room : bookedConferenceRooms) {
                bookedRoomIds.add(room.getId());
            }

            roomEntities = roomRepository.findByIdNotIn(bookedRoomIds);
        }
        else{
            roomEntities = roomRepository.findAll();
        }

        log.info("available rooms for given slot fetched successfully {}", roomEntities);
        return roomEntities;
    }

    private List<ConferenceRoom> findBookedRoomsForRange(Time startTime, Time endTime){
        List<ConferenceRoom> bookedConferenceRooms = bookingRepository.findBookedRoom(startTime, endTime);
        log.info("booked rooms for given slot fetched successfully {}", bookedConferenceRooms);
        return bookedConferenceRooms;
    }

    private List<MaintenanceDetails> findMaintenanceSlotForRange(Time startTime, Time endTime){
        List<MaintenanceDetails> maintenanceDetails = maintenanceRepository.findMaintenanceSlot(startTime,endTime);
        log.info("maintenance details for given slot fetched successfully {}", maintenanceDetails);
        return maintenanceDetails;
    }

    private Time covertStringToTime(String stringTime)  {
        DateFormat formatter = new SimpleDateFormat(TIME_SLOT_FORMAT);
        Date dateFromString = null;
        try {
            dateFromString  = formatter.parse(stringTime);
        } catch (ParseException e) {
            throw new BaseException(BAD_TIME_FORMAT, HttpStatus.BAD_REQUEST);
        }
        Time timeValue = new java.sql.Time(dateFromString.getTime());
        return timeValue;
    }
}
