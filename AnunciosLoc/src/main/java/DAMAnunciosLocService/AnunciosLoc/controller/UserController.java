package DAMAnunciosLocService.AnunciosLoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import DAMAnunciosLocService.AnunciosLoc.service.UserService;
import xml.soap.user2.AddUserRequest;
import xml.soap.user2.AllUsersRequest;
import xml.soap.user2.GetUserRequest;
import xml.soap.user2.UserListResponse;
import xml.soap.user2.UserResponse;

@Endpoint
@Component
public class UserController {
	
	private static final String NAMESPACE_URI = "http://user2.soap.xml";
	
	@Autowired
	private UserService userService;

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public UserResponse addUserAdmin (@RequestPayload AddUserRequest request) {
		System.out.println("Entrando no serviço addUser");
		return userService.addUserAdmin(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserIdRequest")
	@ResponsePayload
	public UserResponse getUserId (@RequestPayload GetUserRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return (UserResponse) userService.getUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllUsersRequest")
	@ResponsePayload
	public UserListResponse getAllUsers (@RequestPayload AllUsersRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getAllUsers(request);
	}

    /*@PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetSaldoRequest")
	@ResponsePayload
    public GetSaldoResponse getSaldo (@RequestPayload GetSaldoRequest request) {
        System.out.println("Entrando no serviço de consultar saldo");
		return userService.getSaldo(request);
    }*/

    /*@PayloadRoot(namespace= NAMESPACE_URI, localPart = "TransferPointsRequest")
	@ResponsePayload
    public TransferPointsResponse getSaldo (@RequestPayload TransferPointsRequest request) {
        System.out.println("Entrando no serviço de transferência de pontos");
		return ciclistaService.transferPoints(request);
    }*/

    /*@PayloadRoot(namespace= NAMESPACE_URI, localPart = "HistoricTrajectRequest")
	@ResponsePayload
    public HistoricTrajectResponse getHistoricTraject (@RequestPayload HistoricTrajectRequest request) {
        System.out.println("Entrando no serviço de carregamento de histórico de trajectória");
		return ciclistaService.getHistoricTraject(request);
    }*/

}