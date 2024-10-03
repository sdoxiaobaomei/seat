package org.chai.seat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.chai.seat.dao.BookingDao;
import org.chai.seat.dao.SeatDao;
import org.chai.seat.entity.Booking;
import org.chai.seat.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService extends ServiceImpl<BookingDao, Booking> {

    private final BookingDao bookingDao;
    private final SeatDao seatDao;

    @Autowired
    public BookingService(BookingDao bookingDao, SeatDao seatDao) {
        this.bookingDao = bookingDao;
        this.seatDao = seatDao;
    }


    /**
     * 预订座位方法。
     *
     * @param seatId 座位的唯一标识 ID
     * @param userId 用户的唯一标识 ID
     * @param date 预订的日期
     * @return true 如果预订成功，否则返回 false
     *
     * 此方法检查指定日期的座位是否已经被预订，若未被预订，则将该座位记录为已预订。
     */
    public boolean bookSeat(Long seatId, Long userId, LocalDate date) {
        // 检查座位是否已被预订
        long count = bookingDao.selectCount(
                new QueryWrapper<Booking>().eq("seat_id", seatId).eq("booking_date", date)
        );
        if (count > 0) {
            return false; // 已被预订
        }

        // 插入新的预订
        Booking booking = new Booking();
        booking.setSeatId(seatId);
        booking.setUserId(userId);
        booking.setBookingDate(date);
        booking.setStatus("已预订");
        bookingDao.insert(booking);
        return true;
    }

    /**
     * 取消座位预订方法。
     *
     * @param seatId 座位的唯一标识 ID
     * @param userId 用户的唯一标识 ID
     * @param date 预订的日期
     * @return true 如果取消成功，否则返回 false
     *
     * 此方法取消指定座位在特定日期的预订记录，前提是该用户之前预订了该座位。
     */
    public boolean cancelBooking(Long seatId, Long userId, LocalDate date) {
        // 根据座位ID和用户ID取消预订
        Booking booking = bookingDao.selectOne(
                new QueryWrapper<Booking>().eq("seat_id", seatId).eq("user_id", userId).eq("booking_date", date)
        );
        if (booking != null) {
            booking.setStatus("已取消");
            bookingDao.updateById(booking);
            return true;
        }
        return false;
    }

    /**
     * 获取指定用户的所有预订记录。
     *
     * @param userId 用户的唯一标识 ID
     * @return List<SeatBooking> 返回该用户的所有预订记录列表
     *
     * 通过用户 ID，查询该用户的历史预订记录，包括已取消的预订。
     */
    public List<Booking> getUserBookings(Long userId) {
        return list(new QueryWrapper<Booking>().eq("user_id", userId));
    }

    /**
     * 按日期查询所有座位的预订信息。
     *
     * @param bookingDate 预订日期
     * @return List<Booking> 返回该日期所有座位的预订记录列表
     */
    public List<Booking> getBookingsByDate(LocalDate bookingDate) {
        return list(new QueryWrapper<Booking>()
                .eq("booking_date", bookingDate)
                .eq("status", "已预订"));
    }

    /**
     * 按用户和日期查询空闲的座位。
     *
     * @param userId 用户的唯一标识 ID
     * @param bookingDate 预订日期
     * @return List<Seat> 返回该用户在该日期可以预订的空闲座位列表
     */
    public List<Seat> getAvailableSeatsByUserAndDate(Long userId, LocalDate bookingDate) {
        // 查询所有座位
        List<Seat> allSeats = seatDao.selectList(null);

        // 查询该用户已预订的座位
        List<Long> bookedSeatIds = bookingDao.selectUserBookingsByDate(userId, bookingDate)
                .stream()
                .map(Booking::getSeatId)
                .toList();

        // 过滤掉已被预订的座位
        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .collect(Collectors.toList());
    }


    /**
     * 获取指定用户在当前月份中所有日期的空闲座位（该用户未预订的座位）。
     *
     * @param userId 用户的唯一标识 ID
     * @return Map<LocalDate, List<Seat>> 返回每个日期和该日期可预订的空座位列表
     */
    public Map<LocalDate, List<Seat>> getAvailableSeatsForUserInCurrentMonth(Long userId) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // 获取用户在当前月的已预订记录
        List<Booking> userBookings = bookingDao.selectUserBookingsInMonth(userId, firstDayOfMonth, lastDayOfMonth);

        // 获取用户每个日期已预订的座位ID
        Map<LocalDate, List<Long>> userBookedSeatsByDate = userBookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getBookingDate,
                        Collectors.mapping(Booking::getSeatId, Collectors.toList())
                ));

        // 查询所有座位
        List<Seat> allSeats = seatDao.selectList(null);

        // 创建返回结果，包含每一天的可预订座位
        Map<LocalDate, List<Seat>> availableSeatsByDate = new HashMap<>();

        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            List<Long> bookedSeatIds = userBookedSeatsByDate.getOrDefault(date, Collections.emptyList());
            List<Seat> availableSeats = allSeats.stream()
                    .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                    .collect(Collectors.toList());
            availableSeatsByDate.put(date, availableSeats);
        }

        return availableSeatsByDate;
    }

}