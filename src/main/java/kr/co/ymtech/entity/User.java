package kr.co.ymtech.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity(name = "user")
public class User {
	
	@Id
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Phone> phoneList;

}
