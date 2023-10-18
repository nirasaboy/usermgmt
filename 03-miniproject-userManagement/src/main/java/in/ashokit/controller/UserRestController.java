package in.ashokit.controller;

import java.util.Map;

import org.apache.tomcat.jni.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccountForm;
import in.ashokit.binding.UserForm;
import in.ashokit.binding.UserMgmtService;

@RestController
public class UserRestController {
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/Login")
	public ResponseEntity<String> login (@RequestBody LoginForm loginForm){
		String status = userMgmtService.login(loginForm);
		return new ResponseEntity<>(status, HttpStatus.OK);
		
		
	}
	
	
	 @GetMapping("/countries")
	  public Map<Integer, String> loadCountries(){
		return userMgmtService.getCountries();
		
	 }
	 
		
		@GetMapping("/states/{countryId}")
		public Map<Integer, String> loadStates(@PathVariable Integer countryId){
			return userMgmtService.getStates(countryId);
		}
		
		
		  @GetMapping("/cities/{stateId}")
		  public Map<Integer, String> loadCities(@PathVariable Integer stateId){
			return userMgmtService.getCities(stateId);
			
			
		}
		
		
            @GetMapping("/email/{email}")		
			public String emailCheck(@PathVariable String email ) {
			return userMgmtService.checkEmail(email);
		}
            
            
            @PostMapping("/user")
            public ResponseEntity <String> userRegistration(@RequestBody UserForm userform){
            	String status = userMgmtService.registerUser(userform);
            	return new ResponseEntity<>(status, HttpStatus.CREATED);
            }
            
            
             @PostMapping("/unlock")           
            public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm unlockAccForm){
            	return new ResponseEntity<>(status, HttpStatus.OK);
            
             }
             
             
             @GetMapping("/forgot/{email}")
             public ResponseEntity<String> forgotpwd(@PathVariable String email){
            	 String status = userMgmtService.forgotPwd(email);
            	 return new ResponseEntity<>(status,HttpStatus.OK);
            	
            	
            	
            }
		}
		
	
	
	
	


