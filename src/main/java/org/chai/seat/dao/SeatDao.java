package org.chai.seat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chai.seat.entity.Seat;

@Mapper
public interface SeatDao extends BaseMapper<Seat> {

}
