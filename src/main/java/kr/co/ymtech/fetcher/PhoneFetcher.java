package kr.co.ymtech.fetcher;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import kr.co.ymtech.dto.PhoneDTO;
import kr.co.ymtech.entity.Phone;
import kr.co.ymtech.repository.PhoneRepository;
import kr.co.ymtech.util.DataUtil;

@Component
public class PhoneFetcher implements GraphQLQueryResolver, GraphQLMutationResolver {

	@Autowired
	private PhoneRepository phoneRepo;
	
	public List<PhoneDTO> getPhone(String phoneNumber) throws Exception {
		List<Phone> phoneList = null;
		List<PhoneDTO> phoneDTOList = null;
		
		if (StringUtils.isEmpty(phoneNumber)) {
			phoneList = phoneRepo.findAll();
		} else {
			phoneList = phoneRepo.findByPhoneNumber(phoneNumber);
		}
		
		phoneDTOList = phoneList.stream()
				.map(phone -> {
					return DataUtil.converterDataToData(phone, PhoneDTO.class);
				})
				.collect(Collectors.toList());
		
		return phoneDTOList;
	}

}
