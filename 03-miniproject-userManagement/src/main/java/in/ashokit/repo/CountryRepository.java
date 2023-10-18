package in.ashokit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster,Serializable> {

}
