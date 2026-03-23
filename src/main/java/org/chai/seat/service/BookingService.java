package org.chai.seat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chai.seat.dao.BookingDao;
import org.chai.seat.dao.SeatDao;
import org.chai.seat.dao.UserDao;
import org.chai.seat.dto.TableRowDTO;
import org.chai.seat.entity.Booking;
import org.chai.seat.entity.Seat;
import org.chai.seat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingDao bookingDao;
    private final SeatDao seatDao;
    private final UserDao userDao;

    @Autowired
    public BookingService(BookingDao bookingDao, SeatDao seatDao, UserDao userDao) {
        this.bookingDao = bookingDao;
        this.seatDao = seatDao;
        this.userDao = userDao;
    }

    public List<TableRowDTO> getTableData(List<String> dates) {
        if (dates == null || dates.isEmpty()) {
            return new ArrayList<>();
        }

        List<LocalDate> localDates = dates.stream().map(LocalDate::parse).collect(Collectors.toList());
        List<Seat> seats = seatDao.selectList(null);
        List<Booking> bookings = bookingDao.selectList(
                new QueryWrapper<Booking>().in("booking_date", localDates)
        );

        // 收集所有 userId 并查询对应的用户名
        Set<String> userIds = bookings.stream()
                .map(Booking::getUserId)
                .collect(Collectors.toSet());
        
        Map<String, String> userIdToUsername = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userDao.selectBatchIds(userIds);
            for (User user : users) {
                userIdToUsername.put(user.getId(), user.getUsername());
            }
        }

        List<TableRowDTO> tableData = new ArrayList<>();
        for (Seat seat : seats) {
            TableRowDTO row = new TableRowDTO();
            row.setSeatId(seat.getId());
            row.setSeatName(seat.getSeatName() + "-" + seat.getSeatGroup());

            Map<String, String> subscriptions = bookings.stream()
                    .filter(b -> b.getSeatId().equals(seat.getId()))
                    .collect(Collectors.toMap(
                            b -> b.getBookingDate().toString(),
                            b -> userIdToUsername.getOrDefault(b.getUserId(), b.getUserId())
                    ));

            row.setSubscriptions(subscriptions);
            tableData.add(row);
        }
        return tableData;
    }

    public boolean bookSeat(String seatId, String username, LocalDate date) {
        final User user = userDao.selectUserByName(username);
        if (user == null) return false;

        // 检查该座位当天是否已被预订
        long seatCount = bookingDao.selectCount(
                new QueryWrapper<Booking>().eq("seat_id", seatId).eq("booking_date", date)
        );
        if (seatCount > 0) {
            return false;
        }

        // 检查该用户当天是否已预订其他座位
        long userCount = bookingDao.selectCount(
                new QueryWrapper<Booking>().eq("user_id", user.getId()).eq("booking_date", date)
        );
        if (userCount > 0) {
            return false; // 用户当天已有其他座位预订
        }

        Booking booking = new Booking();
        booking.setSeatId(seatId);
        booking.setUserId(user.getId());
        booking.setBookingDate(date);
        booking.setStatus("booked");
        bookingDao.insert(booking);
        return true;
    }

    public boolean cancelBooking(String seatId, String username, LocalDate date) {
        // 先根据用户名查询用户 ID
        User user = userDao.selectUserByName(username);
        if (user == null) {
            return false;
        }
        
        Booking booking = bookingDao.selectOne(
                new QueryWrapper<Booking>().eq("seat_id", seatId).eq("user_id", user.getId()).eq("booking_date", date)
        );
        if (booking != null) {
            bookingDao.deleteById(booking.getId());
            return true;
        }
        return false;
    }

    public List<Booking> getUserBookings(String userId) {
        return bookingDao.selectList(new QueryWrapper<Booking>().eq("user_id", userId));
    }

    public List<Booking> getBookingsByDate(LocalDate bookingDate) {
        return bookingDao.selectList(new QueryWrapper<Booking>().eq("booking_date", bookingDate));
    }

    public List<Seat> getAvailableSeatsByUserAndDate(String userId, LocalDate bookingDate) {
        List<Seat> allSeats = seatDao.selectList(null);
        List<String> bookedSeatIds = bookingDao.selectList(
                new QueryWrapper<Booking>().eq("user_id", userId).eq("booking_date", bookingDate)
        ).stream().map(Booking::getSeatId).collect(Collectors.toList());

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .collect(Collectors.toList());
    }

    public Map<LocalDate, List<Seat>> getAvailableSeatsForUserInCurrentMonth(String userId) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Booking> userBookings = bookingDao.selectList(
                new QueryWrapper<Booking>().eq("user_id", userId)
                        .ge("booking_date", firstDayOfMonth)
                        .le("booking_date", lastDayOfMonth)
        );

        Map<LocalDate, List<String>> userBookedSeatsByDate = userBookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getBookingDate,
                        Collectors.mapping(Booking::getSeatId, Collectors.toList())
                ));

        List<Seat> allSeats = seatDao.selectList(null);
        Map<LocalDate, List<Seat>> availableSeatsByDate = new HashMap<>();

        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            List<String> bookedSeatIds = userBookedSeatsByDate.getOrDefault(date, Collections.emptyList());
            List<Seat> availableSeats = allSeats.stream()
                    .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                    .collect(Collectors.toList());
            availableSeatsByDate.put(date, availableSeats);
        }
        return availableSeatsByDate;
    }
}
