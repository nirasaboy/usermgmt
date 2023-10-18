package in.ashokit.binding;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entity.CityMaster;
import in.ashokit.entity.CountryMaster;
import in.ashokit.entity.StateMaster;
import in.ashokit.entity.User;
import in.ashokit.repo.CityRepository;
import in.ashokit.repo.CountryRepository;
import in.ashokit.repo.StateRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {
     
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public String checkEmail(String email) {
	
		User user = userRepo.findByEmail(email);
		
		if(user == null) {
			return "UNIQUE";
		}
		
		return "DUPLICATE";
		
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMaster> countries = countryRepo.findAll();
		
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country ->{
			countryMap.put(country.getCountryId(),country.getCountryname());
			});
			return countryMap;
			
	
	}

	@Override
	public Map<Integer, String> getStatus(Integer countryId) {
	   List<StateMaster> states	= stateRepo.findByCountryId(countryId);
	   
	   Map<Integer, String> stateMap = new HashMap<>();
	   
	   states.forEach(state -> {
		   stateMap.put(state.getStateId(), state.getStateName());
	   });
	   
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		
		List<CityMaster> cities =cityRepo.findByStateId(stateId);
		
		Map<Integer, String> cityMap = new HashMap<>();
		cities.forEach(city-> {;
		
		cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;
	}
		
		
	@Override
	public String registerUser(UserForm user) {
		
		//copy data from binding obj to entity obj
		User entity = new User();
		BeanUtils.copyProperties(userform, entity);
// generate & set Random Pwd
		
		entity.setUserPwd(generateRandomPwd());
		//set Account Status as Locked
		
		entity.setAccStatus("Locked");
		
		userRepo.save(entity);
		
		
		
		//send email to unlock account
		
		String to = userform.getEmail();
		String subject = "Registration Email";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		emailUtils.sendEmail(to, subject, body);
		
		return "User Account Created";
	}
	

	@Override
	public String unlockAccount(UnlockAccountForm accForm) {
		String email = unlockAccForm.getEmail();
				User user = userRepo.findByEmail(email);
		
		if(user!=null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
							user.setUserPwd(unlockAccForm.getNewPwd());
				user.setAccStatus("UNLOCKED");
				userRepo.save(user);
			return "Account Unlocked";
			
			}
			
					
					return "Invalid Temporary Password";
	}
		

	@Override
	public String login(LoginForm loginForm) {
		
	
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());
		
		if(user == null) {
			return "Invalid Credential";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
	return "Success";
		}
	
		

	@Override
	public String forgotPwd(String email) {
		User user = userRepo.findByEmail(email);
		
		
		if(user == null) {
			return "No Account found";
		}
		String subject = "Recover Password";
		String body = readEmailBody ("FORGOT_PWD_EMAIL_BODY.txt",user);
		
		emailUtils .sendEmail(email, subject,body);
		
		return "Password sent to registered email";
	}
	
	
	
	private String generateRandomPwd() {
		
		String text = "ABCDEfghijklmnopqrstqvwxyz1234567890";
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		
		int pwdLength = 6;
		for (int i=1; i<=pwdLength; i++) {
			int index = random.nextInt (text.length());
			sb.append(text.charAt(index));
		}
		return sb.toString();
	}
	
	
	public String readEmailBody(String filename,User user) throws Exception{
		
		
		StringBuffer sb = new StringBuffer();
		
		try(Stream<String> lines = Files.lines(Paths.get(filename))) {
			
			lines.forEach(line -> {
				
				line = line.replace("${FNAME}", user.getFname());
				line = line. replace("${LNAME}",user.getLname());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAIL}", user.getEmail( ));
				line = line.replace("${PWD}", user.getUserPwd());
								sb.append(line);
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			}
		
	}
	
	
	
	
