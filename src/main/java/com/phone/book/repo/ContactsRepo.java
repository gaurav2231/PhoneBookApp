package com.phone.book.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phone.book.entity.Contacts;
import com.phone.book.entity.User;
@Repository
public interface ContactsRepo extends JpaRepository<Contacts, Integer> {
	 
	 User findByName(String name);

	void save(User user);



	
	
}
