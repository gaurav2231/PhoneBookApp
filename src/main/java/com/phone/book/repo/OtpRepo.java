package com.phone.book.repo;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.phone.book.entity.OtpDetails;


@Repository
public interface OtpRepo extends CrudRepository<OtpDetails, Integer> {
  OtpDetails findByOtp(String otp);

boolean existsByOtp(String otp);
	
	
}
