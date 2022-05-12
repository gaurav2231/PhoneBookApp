package com.phone.book.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.phone.book.Jsontoken.Jsontoken;
import com.phone.book.entity.Contacts;
import com.phone.book.entity.EditContactResponse;
import com.phone.book.entity.EditDetailResponse;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.RegisterResponse;
import com.phone.book.entity.User;
import com.phone.book.entity.UserPDFExporter;
import com.phone.book.entity.testBody;
import com.phone.book.message.Message;
import com.phone.book.repo.ContactsRepo;
import com.phone.book.repo.OtpRepo;
import com.phone.book.repo.PhoneBookRepo;
import com.phone.book.service.JwtUtil;
import com.phone.book.service.PhoneBookService;
import com.phone.book.service.PhoneBookServiceImpl;
import com.phone.book.service.UserServices;
@CrossOrigin
@RestController
public class PhoneBookController {
	@Autowired
    private UserServices service;
	
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
	public ResponseEntity<RegisterResponse>  addUser(@Valid  @RequestBody User user)throws Exception
	{
		try {
			
		
    	RegisterResponse response=new RegisterResponse();
    	User verifyUser = phoneBookRepo.findByPhoneNumber(user.getPhoneNumber());
		if(verifyUser==null) {
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
		
		else {
			
			response.setMessage("user already exists");
		    response.setCode(400);
		    response.setStatusCode(400);
			return ResponseEntity.badRequest().body(response);

		}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			RegisterResponse eresponse=new RegisterResponse();
			eresponse.setCode(400);
			eresponse.setStatusCode(400);
			eresponse.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(eresponse);
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
	public ResponseEntity<Object> update(@RequestBody User user )   
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
	   		RegisterResponse response=new RegisterResponse();
		       response.setMessage("User already exists");
		       response.setCode(409);
		       response.setStatusCode(409);
			return ResponseEntity.badRequest().body(response);	
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
            response.setCode(200);
			response.setStatusCode(200);
			response.setMessage("Login Successfully");
			return ResponseEntity.ok(response);	


        }
		else {
			response.setCode(400);
			response.setStatusCode(401);
			response.setMessage("Invalid Credential");
			return ResponseEntity.badRequest().body(response);	
        }
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
	       response.setCode(409);
	       response.setStatusCode(409);

		
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
	public ResponseEntity<Object>  addUser(@RequestBody Contacts contacts)
	{
		if(contactsRepo.existsByphoneNumber(contacts.getPhoneNumber())==false
				||
				contactsRepo.existsByEmail(contacts.getEmail())==false)
			{
			EditContactResponse response=new EditContactResponse();
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
		User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
		contacts.setUser(user);
	

		phoneBookService.addContacts(contacts);
		response.setCode(200);
		response.setStatuscode(200);
		response.setMessage("Contacts added successfully");
		response.setContact(contacts);
		
		return ResponseEntity.ok(response);
			}
		else {
RegisterResponse message=new RegisterResponse();
	   		
	   		message.setMessage("Already Exists");
	   		message.setCode(400);
	   		message.setStatusCode(400);
			return ResponseEntity.badRequest().body(message);
	   	}
		
	}
	
		
	@PutMapping("/editContacts/{id}")  
	public ResponseEntity<Object> editContacts(@PathVariable("id") int id, @RequestBody Contacts contacts)   
	{
  
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
	   	int a=user.getId();
	   	System.out.println(a);
	   	if(contactsRepo.existsByphoneNumber(contacts.getPhoneNumber())==false
				||
				contactsRepo.existsByEmail(contacts.getEmail())==false)
			{
	     contacts.setId(id);
	     contacts.getId();
	     System.out.println("contact id is   "+contacts.getId());
			EditContactResponse response=new EditContactResponse();

	   	contacts.setUser(user);
		response.setCode(200);
		response.setStatuscode(200);
		response.setContact(contacts);
		response.setMessage("contacts Details Updated Successfully");
		
		contacts.setName(contacts.getName());
        phoneBookService.saveOrUpdate(contacts);
		return ResponseEntity.ok(response);
			}
	   	
	   	else {
	   		RegisterResponse message1=new RegisterResponse();
	   		
	   		message1.setCode(404);
	   		message1.setStatusCode(404);
	   		message1.setMessage("Invalid Entry");
	   		System.out.println(message1.getMessage());
			return ResponseEntity.badRequest().body(message1);
	   	}
	}
	
	
	@GetMapping("/allContacts")
	public ResponseEntity<List<Contacts>> addContacts()   
	{
		Contacts contact=new Contacts();
		String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
	   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
		ArrayList<Contacts> contacts= contactsRepo.findByUserAndStatusOrderByIdDesc(user, 0);
		return ResponseEntity.ok(contacts);
	}
	
	
	  @GetMapping("viewContactDetails/{id}")
	  public ResponseEntity<?> viewContactDetails(@PathVariable("id") int id, Contacts contacts) 
	  {  
		  
		  Contacts contact = contactsRepo.findById(id).get();
		  if(contact.isDeleted()) {
			  return ResponseEntity.badRequest().body(new Message("Contact Already Deleted!"));
		  }else {
			  String phoneNumber = phoneBookServiceImpl.getPhoneNumber();
			   	User user = phoneBookRepo.findByPhoneNumber(phoneNumber);
			   	contacts= contactsRepo.findByIdAndUser(id, user);
			   	if(contacts == null) {
			   		Message message=new Message();
			   		message.setMessage("Cant find contact on this token");
			   		return ResponseEntity.badRequest().body(message);
			   	}
			   	return ResponseEntity.ok().body(contacts);
		  }
	 	   
	  }
	  

	  @DeleteMapping("/deleteContact/{id}")
	   public  ResponseEntity<RegisterResponse> deleteContacts(@PathVariable ("id") int id, Contacts contact){
		  RegisterResponse response=new RegisterResponse();
        try {
        	
  		  Contacts foundContact = contactsRepo.findById(contact.getId()).get();
            System.out.println("status "+foundContact.getStatus());
        	if(foundContact.getStatus()==0) {
       	 contact.setId(id);
		  contact.getId();
		  //Contacts foundContact = contactsRepo.findById(contact.getId()).get();
		  foundContact.setStatus(2);
		  Contacts savedContact = contactsRepo.save(foundContact);
		  response.setCode(200);
			response.setStatusCode(200);
			response.setMessage("Contact Removed Successfully");
			return ResponseEntity.ok(response);
			}
        
        	
        	else {
        		response.setCode(404);
        		response.setStatusCode(404);
        		response.setMessage("User does not exists !!!!");
    			return ResponseEntity.ok(response);
        	}
        	
        }
     catch(Exception e)
     {
	 System.out.print(e.getMessage());
	 response.setCode(400);
	 response.setStatusCode(400);
	 response.setMessage(e.getMessage());
	 return ResponseEntity.badRequest().body(response);
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
				
				 final UserDetails userDetails =phoneBookServiceImpl.loadUserByUsername(String.valueOf(user.getPhoneNumber())); 
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
		
	   
	   
	   @DeleteMapping("/deleteMyAccount")
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
	   
	   
	   @GetMapping("/users/export/pdf")
	    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<User> listUsers = service.listAll();
	         
	        UserPDFExporter exporter = new UserPDFExporter(listUsers);
	        exporter.export(response);
	         
	    }
	   
}  