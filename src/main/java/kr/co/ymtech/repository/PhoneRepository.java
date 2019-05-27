package kr.co.ymtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ymtech.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

	public List<Phone> findByPhoneNumber(String phoneNumber);
	
}
