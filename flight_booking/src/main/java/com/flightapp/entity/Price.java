package com.flightapp.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Price {
	private int oneWay;
	private int roundTrip;
	public int getOneWay() {
		return oneWay;
	}
	public void setOneWay(int oneWay) {
		this.oneWay = oneWay;
	}
	public int getRoundTrip() {
		return roundTrip;
	}
	public void setRoundTrip(int roundTrip) {
		this.roundTrip = roundTrip;
	}
	
}
