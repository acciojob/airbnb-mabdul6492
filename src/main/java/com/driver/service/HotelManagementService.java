package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class HotelManagementService {

//    @Autowired
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {

        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {

        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        Map<String, Hotel> allHotels = hotelManagementRepository.getHotelRepo();
        List<String> hotelWithMostFacilities = new ArrayList<>();
        int mostFacilities = 0;

        for(Hotel hotel: allHotels.values()){
            int currFacilities = hotel.getFacilities().size();

            if(mostFacilities < currFacilities){
                mostFacilities = currFacilities;
                hotelWithMostFacilities = new ArrayList<>();
            }

            if(mostFacilities == currFacilities)
                hotelWithMostFacilities.add(hotel.getHotelName());
        }

        if(hotelWithMostFacilities.isEmpty()) return "";

        Collections.sort(hotelWithMostFacilities);
        return hotelWithMostFacilities.get(0);
    }

    public int bookARoom(Booking booking) {
        Hotel hotel = hotelManagementRepository.getHotel(booking.getHotelName());
        if(hotel == null || hotel.getAvailableRooms() < booking.getNoOfRooms())
            return -1;

        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);

        int totalAmount = hotel.getPricePerNight()*booking.getNoOfRooms();
        booking.setAmountToBePaid(totalAmount);
        hotelManagementRepository.addBooking(booking);

        return totalAmount;
    }

    public int getBookings(Integer aadharCard) {
        Map<String, Booking> allBookings = hotelManagementRepository.getBookingRepo();

        int totalBookings = 0;
        for(Booking booking: allBookings.values()){
            if(aadharCard == booking.getBookingAadharCard())
                totalBookings++;
        }

        return totalBookings;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelManagementRepository.getHotel(hotelName);

        Set<Facility> allFacilities = new HashSet<>(hotel.getFacilities());
        allFacilities.addAll(newFacilities);

        List<Facility> facilities = new ArrayList<>(allFacilities);
        hotelManagementRepository.updateFacilities(facilities, hotelName);

        return hotel;
    }
}
