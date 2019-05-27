package kr.co.ymtech.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserDTO {

	private String id;
	private String password;
	private int age;
	private LocalDateTime createDate;
	private List<PhoneDTO> phoneList;
	
}
