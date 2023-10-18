package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table
public class CountryMaster {
	@Id
	private Integer CountryId;
	private String Countryname;

}
