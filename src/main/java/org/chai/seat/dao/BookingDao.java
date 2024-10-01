package org.chai.seat.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chai.seat.entity.Booking;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookingDao extends BaseMapper<Booking> {

        /**
         * 根据预订日期查询所有已预订的座位。
         *
         * @param bookingDate 预订日期
         * @return 预订记录列表
         */
        default List<Booking> selectBookingsByDate(LocalDate bookingDate) {
            return selectList(new QueryWrapper<Booking>()
                    .eq("booking_date", bookingDate)
                    .eq("status", "已预订"));
        }

        /**
         * 根据用户和日期查询该用户已预订的座位。
         *
         * @param userId 用户ID
         * @param bookingDate 预订日期
         * @return 预订记录列表
         */
        default List<Booking> selectUserBookingsByDate(Long userId, LocalDate bookingDate) {
            return selectList(new QueryWrapper<Booking>()
                    .eq("user_id", userId)
                    .eq("booking_date", bookingDate)
                    .eq("status", "已预订"));
        }

        /**
         * 获取用户在指定月份内的预订记录。
         *
         * @param userId 用户ID
         * @param startDate 月份起始日期
         * @param endDate 月份结束日期
         * @return 预订记录列表
         */
        default List<Booking> selectUserBookingsInMonth(Long userId, LocalDate startDate, LocalDate endDate) {
            return selectList(new QueryWrapper<Booking>()
                    .eq("user_id", userId)
                    .between("booking_date", startDate, endDate)
                    .eq("status", "已预订"));
        }
    }


