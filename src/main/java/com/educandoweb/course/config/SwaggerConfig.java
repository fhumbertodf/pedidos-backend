package com.educandoweb.course.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/* 
	 * The request has been fulfilled and resulted in a new resource being created.
	 *  
	 * The newly created resource can be referenced by the URI(s) returned in the entity of the response, 
	 * with the most specific URI for the resource given by a Location header field.
	 *  
	 * The response SHOULD include an entity containing a list of resource characteristics and location(s) 
	 * from which the user or user agent can choose the one most appropriate. 
	 * 
	 * The entity format is specified by the media type given in the Content-Type header field.
	 *  
	 * The origin server MUST create the resource before returning the 201 status code.
	 *  
	 * If the action cannot be carried out immediately, the server SHOULD respond with 202 (Accepted) response instead. */
	private final ResponseMessage m201 = simpleMessage(201, "Criado");	
	
	/* The server has fulfilled the request but does not need to return an entity-body, and might want to return updated metainformation.
	 * The response MAY include new or updated metainformation in the form of entity-headers, which if present SHOULD be associated with the requested variant.*/
	private final ResponseMessage m204 = simpleMessage(204, "Deletado");
	
	/* The request could not be understood by the server due to malformed syntax. 
	 * The client SHOULD NOT repeat the request without modifications. */
	private final ResponseMessage m400 = simpleMessage(400, "Requisição Inválida");
	
	/* The server understood the request, but is refusing to fulfill it. 
	 * Authorization will not help and the request SHOULD NOT be repeated.
	 *  
	 * If the request method was not HEAD and the server wishes to make public why the request has not been fulfilled, 
	 * it SHOULD describe the reason for the refusal in the entity. 
	 * 
	 * If the server does not wish to make this information available to the client, the status code 404 (Not Found) can be used instead.*/
	private final ResponseMessage m403 = simpleMessage(403, "Não autorizado");
	
	/* The server has not found anything matching the Request-URI.
	 * No indication is given of whether the condition is temporary or permanent. */
	private final ResponseMessage m404 = simpleMessage(404, "Não encontrado");
	
	private final ResponseMessage m422 = simpleMessage(422, "Erro de validação");
	
	/* The server encountered an unexpected condition which prevented it from fulfilling the request. */
	private final ResponseMessage m500 = simpleMessage(500, "Erro inesperado");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m403, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204, m403, m404, m500))
				
				.select()
				.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.educandoweb.course.web"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}
}