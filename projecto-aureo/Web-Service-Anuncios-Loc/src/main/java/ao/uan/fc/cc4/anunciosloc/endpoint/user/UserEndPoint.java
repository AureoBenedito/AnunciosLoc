package ao.uan.fc.cc4.anunciosloc.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.anunciosloc.endpoint.user.service.AuthenticationService;
import ao.uan.fc.cc4.anunciosloc.endpoint.user.service.UserService;
import xml.soap.user.*;

@Endpoint
@Component
public class UserEndPoint {
	
	private static final String NAMESPACE_URI = "http://user.soap.xml";
	
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private AuthenticationService auth; 

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
	@ResponsePayload
	public UserResponse login (@RequestPayload LoginRequest request) {
		System.out.println("Entrando no serviço de login login");
		return auth.login(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LogoutRequest")
	@ResponsePayload
	public UserResponse logout (@RequestPayload LogoutRequest request) {
		System.out.println("Entrando no serviço logout");
		return auth.logout(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
	@ResponsePayload
	public UserResponse validateSession (@RequestPayload ValidateTokenRequest request) {
		System.out.println("Entrando no serviço validateSession");
		return auth.validationSession(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public UserResponse addUser (@RequestPayload AddUserRequest request) {
		System.out.println("Entrando no serviço addUser");
		return userService.addUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
	@ResponsePayload
	public UserResponse getUser (@RequestPayload GetUserRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return (UserResponse) userService.getUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllUsersRequest")
	@ResponsePayload
	public UserListResponse getAllUsers (@RequestPayload AllUsersRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getAllUsers(request);
	}

}