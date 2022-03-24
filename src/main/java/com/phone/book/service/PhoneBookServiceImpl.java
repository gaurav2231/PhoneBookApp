package com.phone.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phone.book.entity.Contacts;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.User;
import com.phone.book.message.Message;
import com.phone.book.repo.ContactsRepo;
import com.phone.book.repo.OtpRepo;
import com.phone.book.repo.PhoneBookRepo;
@Service
public class PhoneBookServiceImpl implements PhoneBookService, UserDetailsService{

	@Autowired
	private PhoneBookRepo phoneBookRepo;
	
	@Autowired
	private OtpRepo otpDRepo;
	
	@Autowired
	private ContactsRepo contactsrepo;

	
	@Override
	public void addUser(User user) {
		phoneBookRepo.save(user);
	}
		
	

	@Override
	public List<User> getAll(List<User> user) {
		
		phoneBookRepo.findAll().forEach(user1 -> user.add(user1));
		if(user!=null) {
		return user;
		}
		else {
			Message message=new Message();
			message.setMessage("User not found");
			return (List<User>) ResponseEntity.ok(message);
		}
	}
	

	@Override
	public String getOtp() {
		 Random rnd = new Random();
		    int number = rnd.nextInt(999999);

		    // this will convert any number sequence into 6 character.
		 System.out.println("OTP is : "+String.format("%06d", number)); 
		 String otp=String.format("%06d", number);
		 return otp;
		
	}

	@Override
	public void saveOrUpdate(User user) {
      
       
		phoneBookRepo.save(user);  

		
	}

	@Override
	public void addOtpDetails(OtpDetails otpdetails) {

		otpDRepo.save(otpdetails);
	}

	@Override
	public void check() {
		User user =new User();
		// to check username exist or not
		
phoneBookRepo.findAll().equals(user.getName());
	 System.out.println("inside service class "+phoneBookRepo.findAll().equals(user.getName()));
		
		
	}



	@Override
	public void saveOrUupdate(User user) {
     phoneBookRepo.save(user);
		
		
		
		
	}



	@Override
	public void addContacts(Contacts contacts) {
		
		contactsrepo.save(contacts);

	}



	@Override
	public void saveOrUpdate(Contacts contacts) {
		
		
		contactsrepo.save(contacts);
	}



	@Override
	public void getContactDetails(List<Contacts> contacts) {
	   contactsrepo.findAll().forEach(contacts1 -> contacts.add(contacts1));		
	}



	@Override
	public void viewContactDetails(int id) {
     
		contactsrepo.findById(id).get();
		
	}

     @Override
	public void deleteContacts(int id) {
     contactsrepo.deleteById(id);		
	}



	@Override
	public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
		User user = phoneBookRepo.findByPhoneNumber(phoneNo);
        return  new org.springframework.security.core.userdetails.User(String.valueOf(user.getPhoneNumber()), "", new ArrayList<>());
	}



	@Override
	public void deleteMyAccount(User user) {
		phoneBookRepo.delete(user);
	}



	@Override
	public void loginlogic(User user) {
		Message message=new Message();
		String a =user.getName();
		String name;
		System.out.println("inside db "+ a);
	    user.setName(a);
		user.setPassCode(user.getPassCode());
		System.out.println(user);
		name =user.getName();
		System.out.println("name printed "+name );
		phoneBookRepo.findByName(user.getName());

		User pn =  phoneBookRepo.findByPhoneNumber(user.getPhoneNumber());
		pn.getName();	
		pn.getPassCode();
		System.out.println("Repo name "+phoneBookRepo.existsByName(pn.getName()));
		if(			phoneBookRepo.existsByName(pn.getName())==true

				&&
				phoneBookRepo.existsByPassCode(pn.getPassCode())==true)
		{
			phoneBookRepo.existsByName(pn.getName());
			user.setPassCode(pn.getPassCode());		
			message.setMessage("Login Successfully");
        }
		else {

			message.setMessage("User not Exist");
			
        }
		System.out.println("LOGIN SUCCESS");
	}



	public String getPhoneNumber(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String phoneNumber = null;
        
        if(principal instanceof UserDetails){
            phoneNumber = ((UserDetails)principal).getUsername();
        }
        else{
            phoneNumber = principal.toString();
          }
        return (phoneNumber);
    }

}


	
	
	
		
		
		
		
	


	

		 
		 
	 

	
	

	

	
		
		
	


