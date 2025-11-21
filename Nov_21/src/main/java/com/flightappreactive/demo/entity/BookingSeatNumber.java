package com.flightappreactive.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("booking_seat_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeatNumber {
    @Id
    private Integer id;
    private Integer bookingId;
    private String seatNumber;
}
