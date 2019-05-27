package kr.co.ymtech.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

/**
 * 
 * 
 * @author MJYoun
 * @since 2019.04.15
 *
 */
public class CustomGraphQLException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1L;
	
	/** 에러 코드 */
	private final int errorCode;
	
	public CustomGraphQLException(int errorCode, String errorMessage) {
		super(errorMessage);
		
		this.errorCode = errorCode;
	}
	
	@Override
	public Map<String, Object> getExtensions() {
		Map<String, Object> customAttributes = new LinkedHashMap<>();

		customAttributes.put("errorCode", this.errorCode);
		customAttributes.put("errorMessage", this.getMessage());
		
		return customAttributes;
	}
	
	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return null;
	}

}
