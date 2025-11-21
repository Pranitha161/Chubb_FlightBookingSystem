package com.flightappreactive.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    private Integer id;

    private Integer flightId;     // foreign key to Flight
    private String seatNumber;    // e.g., "A1", "B2"
    private Boolean available;    // true if seat is available
}
