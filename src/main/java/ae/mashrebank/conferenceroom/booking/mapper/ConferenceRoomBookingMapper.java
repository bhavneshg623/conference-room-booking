package ae.mashrebank.conferenceroom.booking.mapper;

import ae.mashrebank.conferenceroom.booking.dto.AvailableRoomResponse;
import ae.mashrebank.conferenceroom.booking.dto.Room;
import ae.mashrebank.conferenceroom.booking.dto.RoomBookingResponse;
import ae.mashrebank.conferenceroom.booking.entity.Booking;
import ae.mashrebank.conferenceroom.booking.entity.ConferenceRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConferenceRoomBookingMapper {

    ConferenceRoomBookingMapper INSTANCE = Mappers.getMapper(ConferenceRoomBookingMapper.class);

    @Mapping(target = "rooms", expression = "java( ae.mashrebank.conferenceroom.booking.mapper.ConferenceRoomBookingMapper.setRoom(roomEntities))")
    AvailableRoomResponse mapToAvailableRoomResponse(Integer dummy,List<ConferenceRoom> roomEntities);

    Room setRoomData(ConferenceRoom roomEntity);


    static List<Room> setRoom(List<ConferenceRoom> roomEntities) {
       return roomEntities.stream().map(
         ConferenceRoomBookingMapper.INSTANCE::setRoomData
        ).collect(Collectors.toList());

    }

    static  String setStatus(){
        return "success";
    }

    @Mapping(target = "room", source = "bookingEntity.conferenceRoom")
    @Mapping(target = "status", expression = "java( ae.mashrebank.conferenceroom.booking.mapper.ConferenceRoomBookingMapper.setStatus())")
    RoomBookingResponse mapToRoomBookingResponse(Booking bookingEntity);



}
