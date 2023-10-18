package in.ashokit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StateMaster;

public interface StateRepository extends JpaRepository<StateMaster, Serializable>{
// select * from states_master where country_id= ?
	 public List<StateMaster>findByCountryId(Integer countryId);
}
