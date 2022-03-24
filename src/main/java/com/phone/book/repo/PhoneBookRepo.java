package com.phone.book.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.phone.book.entity.Contacts;
import com.phone.book.entity.User;

@Repository
public interface PhoneBookRepo extends JpaRepository<User, Integer> {
 User findByName(String name);
 User findByPassCode(String passCode);
 User findByPhoneNumber(String phoneNumber);
 boolean existsByName(String name);
 boolean existsByPassCode(String passCode);
 boolean existsById(int id);
 

 
}
