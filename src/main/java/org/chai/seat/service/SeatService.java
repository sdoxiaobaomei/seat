package org.chai.seat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chai.seat.dao.SeatDao;
import org.chai.seat.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatDao seatDao;

    public List<Seat> getAllSeats() {
        return seatDao.selectList(null);
    }

    public Seat getSeatById(String seatId) {
        return seatDao.selectOne(new QueryWrapper<Seat>().eq("id", seatId));
    }

    public boolean addSeat(Seat seat) {
        return seatDao.insert(seat) > 0;
    }

    public boolean updateSeat(String seatId, Seat seat) {
        return seatDao.update(seat, new QueryWrapper<Seat>().eq("id", seatId)) > 0;
    }

    public boolean deleteSeat(String seatId) {
        return seatDao.delete(new QueryWrapper<Seat>().eq("id", seatId)) > 0;
    }
}
