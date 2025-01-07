package org.chai.seat.service;

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
        return seatDao.selectList(null); // 查询所有座位
    }

    public Seat getSeatById(Long seatId) {
        return seatDao.selectById(seatId); // 根据ID查询座位
    }

    public boolean addSeat(Seat seat) {
        return seatDao.insert(seat) > 0; // 插入座位
    }

    public boolean updateSeat(Long seatId, Seat seat) {
        seat.setId(seatId); // 确保更新的ID正确
        return seatDao.updateById(seat) > 0; // 更新座位
    }

    public boolean deleteSeat(Long seatId) {
        return seatDao.deleteById(seatId) > 0; // 删除座位
    }
}
