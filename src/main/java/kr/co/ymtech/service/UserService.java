package kr.co.ymtech.service;

import java.util.List;

import kr.co.ymtech.dto.UserDTO;

public interface UserService {

	public List<UserDTO> findAll();
	
	public UserDTO find(String userId);
	
}
