package com.phone.book.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phone.book.entity.User;
import com.phone.book.repo.PhoneBookRepo;

@Service
@Transactional
public class UserServices {
	 @Autowired
	    private PhoneBookRepo repo;
	     
	    public List<User> listAll() {
	        return repo.findAll(Sort.by("email").ascending());
	    }
}
