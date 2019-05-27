package kr.co.ymtech.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ymtech.dto.UserDTO;
import kr.co.ymtech.provider.CustomProvider;
import kr.co.ymtech.service.UserService;

@RestController
@CrossOrigin
public class Controller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomProvider prov;
	
	@Value("classpath:schema.graphqls")
	private Resource resource;
	
	@RequestMapping(path = "/users",  method = RequestMethod.GET)
	public ResponseEntity<Object> findAll() {
		List<UserDTO> userList = userService.findAll();
		
		return ResponseEntity.ok(userList);
	}
	
	@RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> findById(@PathVariable("id") String id) {
		UserDTO user = userService.find(id);
		
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public ResponseEntity<Object> execute(HttpServletRequest request, @RequestBody String query) {
//		ExecutionResult result = prov.execute(query);
		Map<String, Object> result = prov.execute(query, request);
		throw new NullPointerException("Test Exception");
		
//		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(path = "/schema", method = RequestMethod.GET)
	public ResponseEntity<Object> schema() throws IOException {
		Path path = resource.getFile().toPath();
		List<String> lines = Files.readAllLines(path);
		String schema = String.join("\n", lines);
		
		return ResponseEntity.ok(schema);
	}
	
}
