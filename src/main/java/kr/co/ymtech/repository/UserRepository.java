package kr.co.ymtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ymtech.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
}
