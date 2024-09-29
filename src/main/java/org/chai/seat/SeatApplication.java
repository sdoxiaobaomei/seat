package org.chai.seat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.chai.seat.dao")
public class SeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatApplication.class, args);
    }
}
