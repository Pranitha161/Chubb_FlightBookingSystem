package com.flightapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Passenger;
import com.flightapp.repository.PassengerRepository;

@Service
public class PassengerService {
	@Autowired
	PassengerRepository passengerRepo;
	public ResponseEntity<?> getPassengerById(Integer passengerId){
		Map<String, String> response = new HashMap<>();
		 List<Passenger> passengerOpt=passengerRepo.findById(passengerId).stream().toList();
		 if(passengerOpt.isEmpty()) {
		    	response.put("message", "No passenger with the id"+passengerId);
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		    }else {
		    	return ResponseEntity.status(HttpStatus.OK).body(passengerOpt);
		    }
	}
	public ResponseEntity<?> getPassengerByEmail(String email){
		Map<String, String> response = new HashMap<>();
		Passenger passenger = passengerRepo.findByEmail(email);
		 if(passenger==null) {
		    	response.put("message", "No passenger available");
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		    }else {
		    	return ResponseEntity.status(HttpStatus.OK).body(passenger);
		    }
	}
	public ResponseEntity<?> savePassenger(Passenger passenger){
		Map<String,String> response=new HashMap<>();
		Passenger passengers = passengerRepo.findByEmail(passenger.getEmail());
			if(passengers!=null){
			response.put("message","Passenger already present ");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}else{
			passengerRepo.save(passenger);
			response.put("message","Passenger successfully added");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}	
	}
	public ResponseEntity<?> updateById(Passenger p,Integer id){
		Map<String,String> response=new HashMap<>();
		return passengerRepo.findById(id).map(existingPassenger->{
			existingPassenger.setName(p.getName());
	        existingPassenger.setEmail(p.getEmail());
	        response.put("message","Passenger updated successfully");
			return ResponseEntity.status(HttpStatus.OK).body(response);
	        
		}).orElseGet(()->{
			response.put("message","No passenger with id"+id);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		});
	}
	public ResponseEntity<?> deleteById(Integer id){
		Map<String,String> response=new HashMap<>();
		return passengerRepo.findById(id).map(existingPassenger->{
			passengerRepo.delete(existingPassenger);
			response.put("message","Passenger deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}).orElseGet(()->
		{
			response.put("message","No passenger with id"+id);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		);
	}
}
