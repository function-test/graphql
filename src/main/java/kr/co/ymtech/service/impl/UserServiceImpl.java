package kr.co.ymtech.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ymtech.dto.PhoneDTO;
import kr.co.ymtech.dto.UserDTO;
import kr.co.ymtech.entity.Phone;
import kr.co.ymtech.entity.User;
import kr.co.ymtech.repository.UserRepository;
import kr.co.ymtech.service.UserService;
import kr.co.ymtech.util.DataUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<UserDTO> findAll() {
		List<User> userList = userRepo.findAll();
		List<UserDTO> userDTOList = userList.stream()
				.map(user -> {
					UserDTO userDTO = DataUtil.converterDataToData(user, UserDTO.class);
					
					List<Phone> phoneList = user.getPhoneList();
					List<PhoneDTO> phoneDTOList = phoneList.stream()
							.map(phone -> DataUtil.converterDataToData(phone, PhoneDTO.class))
							.collect(Collectors.toList());
					userDTO.setPhoneList(phoneDTOList);
					
					return userDTO;
				})
				.collect(Collectors.toList());
		
		return userDTOList;
	}

	@Override
	public UserDTO find(String userId) {
		Optional<User> userOp = userRepo.findById(userId);
		
		if (userOp.isPresent()) {
			User user = userOp.get();
			UserDTO userDTO = DataUtil.converterDataToData(user, UserDTO.class);
			
			List<Phone> phoneList = user.getPhoneList();
			List<PhoneDTO> phoneDTOList = phoneList.stream()
					.map(phone -> DataUtil.converterDataToData(phone, PhoneDTO.class))
					.collect(Collectors.toList());
			userDTO.setPhoneList(phoneDTOList);
			
			return userDTO;
		} else {
			return null;
		}
	}

}
