package kr.co.ymtech.fetcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import graphql.schema.DataFetchingEnvironment;
import kr.co.ymtech.dto.PhoneDTO;
import kr.co.ymtech.dto.UserDTO;
import kr.co.ymtech.entity.Phone;
import kr.co.ymtech.entity.User;
import kr.co.ymtech.exception.CustomGraphQLException;
import kr.co.ymtech.repository.UserRepository;
import kr.co.ymtech.util.DataUtil;

@Component
public class UserFetcher implements GraphQLMutationResolver, GraphQLQueryResolver {

	@Autowired
	private UserRepository userRepo;
	
	public List<UserDTO> getUser(String id, DataFetchingEnvironment environment) {
		List<UserDTO> userDTOList = null;
		HttpServletRequest request = environment.getContext();
		System.out.println(DataUtil.toString(request.getHeaderNames()));
		
		if (StringUtils.isEmpty(id)) {
			List<User> userList = userRepo.findAll();
			userDTOList = userList.stream()
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
		} else {
			throw new CustomGraphQLException(555, "custom Error");
//			throw new GraphQLException("hello world");
//			
//			Optional<User> userOp = userRepo.findById(id);
//			
//			if (userOp.isPresent()) {
//				User user = userOp.get();
//				UserDTO userDTO = DataUtil.converterDataToData(user, UserDTO.class);
//				
//				List<Phone> phoneList = user.getPhoneList();
//				List<PhoneDTO> phoneDTOList = phoneList.stream()
//						.map(phone -> DataUtil.converterDataToData(phone, PhoneDTO.class))
//						.collect(Collectors.toList());
//				userDTO.setPhoneList(phoneDTOList);
//				
//				userDTOList = new ArrayList<UserDTO>();
//				userDTOList.add(userDTO);
//			}
		}
		
		return userDTOList;
	}
	
	public UserDTO updateUser(UserDTO userDTO) {
		UserDTO updatedUser = null;
		
		Optional<User> userOp = userRepo.findById(userDTO.getId());
		
		if (userOp.isPresent()) {
			User user = userOp.get();
			
			user.setPassword(userDTO.getPassword())
				.setAge(userDTO.getAge());
			
			userRepo.save(user);
			updatedUser = DataUtil.converterDataToData(user, UserDTO.class);
		}
		
		return updatedUser;
	}
	
}
