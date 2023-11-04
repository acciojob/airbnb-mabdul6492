package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class HotelManagementRepository {
    Map<String, Hotel> hotelRepo;
    Map<Integer, User> userRepo;
    Map<String, Booking> bookingRepo;

    public HotelManagementRepository() {
        hotelRepo = new HashMap<>();
        userRepo = new HashMap<>();
        bookingRepo = new HashMap<>();
    }

    public String addHotel(Hotel hotel) {
        if(hotel == null
          || hotel.getHotelName() == null
          || hotelRepo.containsKey(hotel.getHotelName()))
            return "FAILURE";

        hotelRepo.put(hotel.getHotelName(), hotel);

        return "SUCCESS";
    }

    public Integer addUser(User user) {
        Integer aadharCardNo = user.getaadharCardNo();
        userRepo.put(aadharCardNo, user);

        return aadharCardNo;
    }

    public Map<String, Hotel> getHotelRepo() {
        return hotelRepo;
    }

    public Map<Integer, User> getUserRepo() {
        return userRepo;
    }

    public Map<String, Booking> getBookingRepo() {
        return bookingRepo;
    }

    public Hotel getHotel(String hotelName) {
        return hotelRepo.get(hotelName);
    }

    public void addBooking(Booking booking) {
        bookingRepo.put(booking.getBookingId(), booking);
    }

    public void updateFacilities(List<Facility> facilities, String hotelName) {
        hotelRepo.get(hotelName).setFacilities(facilities);
    }
}
