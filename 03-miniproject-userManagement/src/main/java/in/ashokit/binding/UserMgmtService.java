package in.ashokit.binding;

import java.util.Map;

public interface UserMgmtService {

	public String checkEmail(String email);
	
	public Map<Integer, String > getCountries();
	
	public Map<Integer, String> getStatus(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	public String registerUser(UserForm user);
	
	public String unlockAccount(UnlockAccountForm accForm);
	
	public String login(LoginForm loginForm);
	
	public String forgotPwd(String email);
	
}
