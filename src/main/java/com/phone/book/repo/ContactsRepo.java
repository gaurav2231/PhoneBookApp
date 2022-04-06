package com.phone.book.repo;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.phone.book.entity.Contacts;
import com.phone.book.entity.User;
@Repository
public interface ContactsRepo extends CrudRepository<Contacts, Integer> {
	 
	 User findByName(String name);

	void save(User user);
	
	// void save(Contacts contacts);

	void save(int id);

	boolean existsByphoneNumber(String phoneNumber);

	boolean existsByEmail(String email);
	
	ArrayList<Contacts> findByUserAndStatus(User user, int status);

	Contacts findByStatus(int status);

	Contacts findByIdAndUser(int id, User user);


//	@Modifying
//	@Transactional
//	@Query(value="update contacts set status=?2 where id=?1", nativeQuery = true)
//	void updateStatusById(int id, int status);


	
	
}
