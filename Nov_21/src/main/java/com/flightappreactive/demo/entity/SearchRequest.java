package com.flightappreactive.demo.entity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
	private String fromPlace;
	private String toPlace;
	private LocalDate date;
}

