package kr.co.ymtech.provider.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.coxautodev.graphql.tools.SchemaParser;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import kr.co.ymtech.provider.CustomProvider;

@Component
public class CustomProviderImpl implements CustomProvider {

	@Autowired
	private ApplicationContext context;
	
	@Value("classpath:schema.graphqls")
	private Resource resource;
	
	private GraphQL graphQL;

	@PostConstruct
	private void init() throws IOException {
		String[] beanNames = context.getBeanNamesForType(GraphQLResolver.class);
		final GraphQLResolver<?>[] resolvers = new GraphQLResolver<?>[beanNames.length];
		for (int i = 0; i < beanNames.length; i++) {
			resolvers[i] = (GraphQLResolver<?>) context.getBean(beanNames[i]);
		}

		GraphQLSchema schema = SchemaParser.newParser()
				.file(resource.getFilename())
				.resolvers(resolvers)
				.build()
				.makeExecutableSchema();
		graphQL = GraphQL.newGraphQL(schema).build();
	}
	
	@Override
	public Map<String, Object> execute(String query, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(query).context(request).build();
//		ExecutionResult executionResult = graphQL.execute(query);
		ExecutionResult executionResult = graphQL.execute(executionInput);
		
		result.put("data", executionResult.getData());
		
		List<Map<String, Object>> errors = executionResult.getErrors()
				.stream()
				.map(error -> {
					// custom exception -> code, message
					Map<String, Object> extension = error.getExtensions();
					
					if (extension == null) {
						extension = new HashMap<String, Object>();
						extension.put("errorCode", 11);
						extension.put("errorMessage", error.getMessage());
					}
					
					return extension;
				})
				.collect(Collectors.toList());

		result.put("errors", errors);
		
		return result;
	}

}
