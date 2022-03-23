package com.phone.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonToken;
import com.phone.book.Jsontoken.Jsontoken;
import com.phone.book.entity.Contacts;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.User;
import com.phone.book.entity.testBody;
import com.phone.book.message.Message;
import com.phone.book.repo.ContactsRepo;
import com.phone.book.repo.OtpRepo;
import com.phone.book.repo.PhoneBookRepo;
import com.phone.book.service.JwtUtil;
import com.phone.book.service.PhoneBookService;
import com.phone.book.service.PhoneBookServiceImpl;

@RestController
public class PhoneBookController {
	@Autowired
	private PhoneBookRepo phoneBookRepo;
	
	@Autowired
	 private PhoneBookService phoneBookService;
	
	@Autowired
	 private PhoneBookServiceImpl phoneBookServiceImpl;
	
	@Autowired
	private ContactsRepo contactsRepo;
	
	@Autowired
	private OtpRepo otpRepo;
	
	@Autowired
    private JwtUtil jwtToken;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
    @ResponseStatus(value=HttpStatus.OK,reason = "Registered successfully")
	@PostMapping("/register")
	public ResponseEntity<Message>  addUser(@RequestBody User user)
	{
		Message message=new Message();
		String otp = phoneBookService.getOtp();
		OtpDetails otpDetails=new OtpDetails();
		otpDetails.setOtp(otp);
		otpDetails.setUser(user);
		otpRepo.save(otpDetails);
		
		//Contacts contacts = new Contacts();
		//contacts.setCountryCode(user.getCountryCode());
		//contacts.setEmail(user.getEmail());
		//contacts.setPhoneNumber(user.getPhoneNumber());
		//contacts.setName(user.getName());
		//contacts.setUser(user);
		//contactsRepo.save(contacts);
	    phoneBookService.addUser(user);
		message.setMessage("Registered successfully");
		
		return ResponseEntity.ok(message);
	}
    
    
    @GetMapping("/hello")
	public String  hello() 
	{
        return "Hello";
	}
    
    
	@GetMapping("/getuser")
	public ResponseEntity<String>  getUserDetails() 
	{
        List<User> user=new ArrayList<>();
		phoneBookService.getAll(user);
        return ResponseEntity.ok().body("All users are: "+user);
	}
		
	
	@PutMapping("/editDetails")  
	public ResponseEntity<Message> update(@RequestBody User user )   
	{  
		Message message=new Message();		
		message.setMessage("User Details Edited Successfully");
		user.setCreated(user.getCreated());
		phoneBookService.saveOrUpdate(user); 
		return ResponseEntity.ok(message);
	} 
	
	/*
	 * @PostMapping("/saveOtp") public ResponseEntity<Message>
	 * saveOtpInDatabase(@RequestBody User user) { Message message=new Message();
	 * String otp= phoneBookService.getOtp(); 
	 * User foundUser =
	 * phoneBookRepo.findByPhoneNumber(user.getPhoneNumber()); OtpDetails otpDetails
	 * = new OtpDetails(); otpDetails.setOtp(phoneBookService.getOtp());
	 * otpDetails.setUser(foundUser); phoneBookService.addOtpDetails(otpDetails);
	 * message.setMessage("Otp has been generated and saved in database"); return
	 * ResponseEntity.ok(message);
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	@PostMapping("/login")
	public ResponseEntity<Message> loginUser(@RequestBody User user , String name)
	{
		Message message=new Message();
		String a =user.getName();
		
		System.out.println("inside db "+ a);
	    user.setName(a);
		user.setPassCode(user.getPassCode());
		System.out.println(user);
		name =user.getName();
		System.out.println("name printed "+name );
		phoneBookRepo.findByName(user.getName());

		System.out.println("Repo name "+phoneBookRepo.existsByName(user.getName()));
		if(			phoneBookRepo.existsByName(a)==true

				&&
				phoneBookRepo.existsByPassCode(user.getPassCode())==true)
		{
			phoneBookRepo.existsByName(a);
			user.setPassCode(user.getPassCode());		
			message.setMessage("Login Successfully");
        }
		else {

			message.setMessage("User not Exist");
			
        }
        return ResponseEntity.ok(message);
		
	}
	
	
	@PutMapping("/ChangePhoneNumber")
	public ResponseEntity<Message> updateNumber(@RequestBody User user)  
	{
			Message message=new Message();		
			message.setMessage("Phonenumber has been changed and saved in database.");
			user.setPhoneNumber(user.getPhoneNumber());
            
			phoneBookService.saveOrUpdate(user); 
			return ResponseEntity.ok(message);	
	}
	
	
	@PostMapping("/addContacts")
	public ResponseEntity<Message>  addUser(@RequestBody Contacts contacts)
	{
		Message message=new Message();
		phoneBookService.addContacts(contacts);
		message.setMessage("Contacts added successfully");
		return ResponseEntity.ok(message);
	}
		
	
	@PutMapping("/editContacts")  
	public ResponseEntity<Message> editContacts(@RequestBody Contacts contacts)   
	{
		Message message=new Message();	
		contacts.setName(contacts.getName());
        phoneBookService.saveOrUpdate(contacts);
        message.setMessage("Contact edited and saved successfully");
		return ResponseEntity.ok(message);
	}
	
	
	@GetMapping("/allContacts")
	public ResponseEntity<List<Contacts>> addContacts( )   
	{
		List<Contacts> contacts=new ArrayList<Contacts>();
		Message message=new Message();	
        phoneBookService.getContactDetails(contacts);
		message.setMessage("All contacts details are given below:-");
		return ResponseEntity.ok(contacts);
	}
	
	
	  @GetMapping("viewContactDetails/{id}")
	  public ResponseEntity<Contacts> viewContactDetails(@PathVariable("id") int id, Contacts contacts) {
		  
	 	   return ResponseEntity.ok().body(contactsRepo.findById(id).get());
	  }
	  
	  
	   @DeleteMapping("/deleteContact/{id}")
	   public ResponseEntity<Message> deleteContacts(@PathVariable("id") int id)
	    {
			Message message=new Message();	
	        phoneBookService.deleteContacts(id);
		    message.setMessage("USer Removed Successfully"); 
		    return ResponseEntity.ok(message);
	    }
	  
	   
		/*
		 * @PostMapping("/otpCheck") public ResponseEntity<Message>
		 * otpCheck(@RequestBody OtpDetails otpDetails, User user, String otp) { Message
		 * message=new Message();
		 * 
		 * 
		 * message.setMessage("OTP CHECKED SUCCESSFULLY"); return
		 * ResponseEntity.ok(message); }
		 */
	   
	   
	   
	   @PostMapping("/checkOTP")
		  public ResponseEntity<Jsontoken> checkOTP(@RequestBody testBody body)throws Exception{
		   try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getPhoneNumber(), ""));
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("Incorrect phoneNumber or password", e);
	        }
			   
	      User user = phoneBookRepo.findByPhoneNumber(Long.parseLong(body.getPhoneNumber()));
	      OtpDetails otp = user.getOtpDetails();
	      String token = "Error";
		  if(otp.getOtp().matches(body.getOtp())) {

			  	user.setStatus(1);
			  	phoneBookService.saveOrUpdate(user);
			  	
			  	
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
					System.out.println("Login Successfully");
		        }
				else {

                    System.out.println("User not Exist");					
		        }
			  	
				final UserDetails userDetails = phoneBookServiceImpl.loadUserByUsername(String.valueOf(user.getPhoneNumber()));
		        token = jwtToken.generateToken(userDetails);

		  } 
		  else {
			  user.setStatus(0);

		  }
		  user.setPhoneNumber(user.getPhoneNumber());
		  user.setCountryCode(user.getCountryCode());
			/*
			 * User pn = phoneBookRepo.findByPhoneNumber(user.getPhoneNumber());
			 * //System.out.println(pn); pn.getName(); pn.getPassCode();
			 */
 		  Jsontoken jsontoken=new Jsontoken();
		 System.out.println("country Code  "+user.getCountryCode());
		 System.out.println("phoneNumber  "+user.getPhoneNumber());
		 jsontoken.setJsontoken(token);
	    	return ResponseEntity.ok().body(jsontoken);

	
	 }
		
	   
@DeleteMapping("/deleteMyAccount")
public ResponseEntity<Message> deleteMyAccount(@RequestBody User user){
	Message message=new Message();
	message.setMessage("Account Removed Successfully");
	phoneBookService.deleteMyAccount(user);
	System.out.println("Account Removed Successfully");
	return ResponseEntity.ok(message);
	
}
	   
}  

		 