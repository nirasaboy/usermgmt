package in.ashokit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.User;

public interface UserRepository extends JpaRepository<User,Serializable>{
     
	//select * from user_master where email=?
	public String findByEmail(String email);
}
