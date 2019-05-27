package kr.co.ymtech.provider;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CustomProvider {

	Map<String, Object> execute(String query, HttpServletRequest request);
	
}
