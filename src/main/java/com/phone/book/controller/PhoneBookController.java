package com.phone.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.phone.book.Jsontoken.Jsontoken;
import com.phone.book.entity.Contacts;
import com.phone.book.entity.EditContactResponse;
import com.phone.book.entity.EditDetailResponse;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.RegisterResponse;
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
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse>  addUser(@Validated @RequestBody User user)throws Exception{
	RegisterResponse response=new RegisterResponse();

	try {
    //  JSONObject obj=new JSONObject();
   
    	String otp = phoneBookService.getOtp();
		OtpDetails otpDetails=new OtpDetails();
		otpDetails.setOtp(otp);
		otpDetails.setUser(user);
		otpRepo.save(otpDetails);
		
	    phoneBookService.addUser(user);
	    response.setMessage("Registered Successfully");
	    response.setCode(200);
	    response.setStatusCode(200);
		  return ResponseEntity.ok(response);

	}
        catch (Exception e) {
              	    response.setMessage("Provide Valid Credentials");
      	  return new ResponseEntity<RegisterResponse>(HttpStatus.NOT_FOUND);
        }
 	}
	
	
    
	@GetMapping("/getuser")
	public ResponseEntity<List<User>>  getUserDetails() 
	{
        List<User> user=new ArrayList<>();
		phoneBookService.getAll(user);
        return ResponseEntity.ok().body(user);
	}
		
	
	@PutMapping("/editDetails")  
	public ResponseEntity<EditDetailResponse> update(@RequestBody User user )   
	{  
		String c=user.getName();
		String b=user.getEmail();
	   	 user.setName(c);
	   	 user.setEmail(b);
	   	 System.out.println("out " + c+" "+b);
	   	String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	 user = phoneBookRepo.findByPhoneNumber(phoneNumber);
	   	 
	   	 System.out.println("New  " + user.getName());
	   	 System.out.println("New "+ user.getEmail());
	   	if(phoneBookRepo.existsByName(c)==true || phoneBookRepo.existsByEmail(b)==true) 
	   	{
	   		EditDetailResponse response=new EditDetailResponse();
		       response.setMessage("User already exists");
			return ResponseEntity.ok(response);	
	   	}
	   		else{
	   	 user.setName(c);
	   	 user.setEmail(b);
	   	 phoneBookService.saveOrUpdate(user);
	   	  
		EditDetailResponse response=new EditDetailResponse();
		response.setCode(200);
		response.setStatuscode(200);
		response.setUser(user);
		response.setMessage("User Details Updated Successfully");
		user.setCreated(user.getCreated());
		return ResponseEntity.ok().body(response);
	} 
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<RegisterResponse> loginUser(@RequestBody User user , String name)
	{
      		RegisterResponse response=new RegisterResponse();
		user.setPassCode(user.getPassCode());
		user.setCountryCode(user.getCountryCode());
		user.setPhoneNumber(user.getPhoneNumber());
		System.out.println(user);
		if(phoneBookRepo.existsByphoneNumber(user.getPhoneNumber())==true
			&&
				phoneBookRepo.existsByPassCode(user.getPassCode())==true
			&&
			phoneBookRepo.existsByCountryCode(user.getCountryCode())==true)
		{
			user.setPassCode(user.getPassCode());
			user.setPhoneNumber(user.getPhoneNumber());
			user.setCountryCode(user.getCountryCode());
			response.setCode(200);
			response.setStatusCode(200);
			response.setMessage("Login Successfully");
        }
		else {
			response.setCode(400);
			response.setStatusCode(401);
			response.setMessage("Invalid Credential");
			return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
	}
	
	
	@PutMapping("/ChangePhoneNumber")
	public ResponseEntity<RegisterResponse> updateNumber(@RequestBody User user)  
	{
		
		String b=user.getPhoneNumber();
	   	 user.setPhoneNumber(user.getPhoneNumber());
		
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	 user = phoneBookRepo.findByPhoneNumber(phoneNumber);
	   	 System.out.println("out " + user.getPhoneNumber());
	   	
	   	  
	   	 System.out.println(b);
	   	 
		
		if(phoneBookRepo.existsByphoneNumber(b)==true) {
	       
		
		RegisterResponse response=new RegisterResponse();
	       response.setMessage("Phone number already exists");

		
		return ResponseEntity.ok(response);	

	}else {
       RegisterResponse response=new RegisterResponse();
		
       user.setPhoneNumber(b);
   	 
   	 System.out.println(b);
   	  
   	 response.setCode(200);
       response.setStatusCode(200);
       response.setMessage("Phonenumber Updated Successfully");
            
			phoneBookService.saveOrUpdate(user); 
			return ResponseEntity.ok(response);	
	}}
	
	
	@PostMapping("/addContacts")
	public ResponseEntity<RegisterResponse>  addUser(@RequestBody Contacts contacts)
	{
		RegisterResponse response=new RegisterResponse();
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
		User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
		contacts.setUser(user);
	

		phoneBookService.addContacts(contacts);
		//System.out.println(user);
		response.setCode(200);
		response.setStatusCode(200);
		response.setMessage("Contacts added successfully");
		return ResponseEntity.ok(response);
	}
	
		
	
	@PutMapping("/editContacts")  
	public ResponseEntity<EditContactResponse> editContacts(@RequestBody Contacts contacts)   
	{
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
	   	int a=user.getId();
	   	System.out.println(a);
	   	contacts.setUser(user);
		EditContactResponse response=new EditContactResponse();
		response.setCode(200);
		response.setStatuscode(200);
		response.setContact(contacts);
		response.setMessage("contacts Details Updated Successfully");
		//contacts.setCreated(contacts.getCreated());	
		contacts.setName(contacts.getName());
        phoneBookService.saveOrUpdate(contacts);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/allContacts")
	public ResponseEntity<List<Contacts>> addContacts()   
	{
		List<Contacts> contacts=new ArrayList<Contacts>();
		Message message=new Message();	
        phoneBookService.getContactDetails(contacts);
		message.setMessage("All contacts details are given below:-");
		return ResponseEntity.ok(contacts);
	}
	
	
	  @GetMapping("viewContactDetails/{id}")
	  public ResponseEntity<Contacts> viewContactDetails(@PathVariable("id") int id, Contacts contacts) 
	  {
	 	   return ResponseEntity.ok().body(contactsRepo.findById(id).get());
	  }
	  

//	  @PutMapping("/deleteContact/{id}")
//	   public  void deleteContacts(@PathVariable("id") int id,Contacts contact){
//		  System.out.println("contact id in c table"+contact.getId());
//		  System.out.println("given contact id"+id);
//
//	  if(id==contact.getId()) {
//
//	 contact.setStatus(2);
//	  System.out.println("true");
//
//	  phoneBookService.saveOrUpdate(contact);
//
//	  }
//	  else {
//		  System.out.println("false");
//	  }
//	}
	  
	  
	   @PutMapping("/deleteContact/{id}")
	   public ResponseEntity<RegisterResponse> deleteContacts(@PathVariable("id") int id,Contacts contact)
	    {
      // 	System.out.println("start api/////////");

			String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
		   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
		   	System.out.println("contact id in contact  "+contact.getId());
            System.out.println("UserId in Contact  "+user.getId());
            
           // if(id==contact.getId())
           if(contactsRepo.existsById(user.getId())==true && contactsRepo.existsById(contact.getId())==true) {
            	contact.setStatus(2);
                 	System.out.println("start ");

            	//contactsRepo.save(contact);    
              	phoneBookService.saveOrUpdate(contact);
                 	//System.out.println("end api/////////");

    	RegisterResponse response=new RegisterResponse();
    		   response.setCode(200);
    		   response.setStatusCode(200);
    		   response.setMessage("Successfully Deleted");
   		    return ResponseEntity.ok(response);

            }
            else {
            	RegisterResponse response=new RegisterResponse();
    		   	
     		   response.setCode(400);
     		   response.setStatusCode(400);
     		   response.setMessage("User not Found");
     		    return ResponseEntity.ok(response);
            }
		   
	    }
	  

	   
	   @PostMapping("/checkOTP")
		  public ResponseEntity<Jsontoken> checkOTP(@RequestBody testBody body)throws Exception{
		   try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getPhoneNumber(), ""));
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("Invalid Credentials", e);
	        }
			   
	      User user = phoneBookRepo.findByPhoneNumber(body.getPhoneNumber());
	      OtpDetails otp = user.getOtpDetails();
	    String token = "Error";
		  if(otp.getOtp().matches(body.getOtp())) {

			  	user.setStatus(1);
			  	phoneBookService.saveOrUpdate(user);
			  	User pn =  phoneBookRepo.findByPhoneNumber(user.getPhoneNumber());
			//  	System.out.println("phone no row is   "+pn);
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
				
				// Generate Token 
				
				 final UserDetails userDetails =
			 phoneBookServiceImpl.loadUserByUsername(String.valueOf(user.getPhoneNumber())); 
				 token = jwtToken.generateToken(userDetails);
				 
		  } 
		  else {
	 		Jsontoken jsontoken=new Jsontoken();
	 		jsontoken.setCode(400);
	 		jsontoken.setStatuscode(401);
	 		jsontoken.setToken("Not Created Please Provide Valid Credential");
	 		jsontoken.setMessage("Invalid Credential");
			user.setStatus(0);
			  return ResponseEntity.badRequest().body(jsontoken);

		  }
		  user.setPhoneNumber(user.getPhoneNumber());
		  user.setCountryCode(user.getCountryCode());
			
 		 Jsontoken jsontoken=new Jsontoken();
		 System.out.println("country Code  "+user.getCountryCode());
		 System.out.println("phoneNumber  "+user.getPhoneNumber());
		 jsontoken.setToken(token);
		 jsontoken.setCode(200);
		 jsontoken.setStatuscode(200);
		 jsontoken.setMessage("OTP Verified Successfully");
	    	return ResponseEntity.ok().body(jsontoken);
	 }
		
	   
	   
	   @PutMapping("/deleteMyAccount")
	   public ResponseEntity<Message> deleteMyAccount(){
		
	   	Message message=new Message();
	   	String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
	   	message.setMessage("Account Removed Successfully");
	   	System.out.println(phoneNumber);
	   	user.setStatus(2);
	   	phoneBookService.saveOrUpdate(user);
	       return ResponseEntity.ok(message);
	   }  
	   

}  

		 