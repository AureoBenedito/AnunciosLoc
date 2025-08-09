package ao.uan.fc.cc4.anunciosloc.endpoint.user.service;

import ao.uan.fc.cc4.anunciosloc.bd.user.UserModel;
import ao.uan.fc.cc4.anunciosloc.bd.user.UserRepository;
import ao.uan.fc.cc4.anunciosloc.utils.HashPassword;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.user.*;

import java.util.List;
import java.util.Optional;  

@Service
public class UserService {

    @Autowired
    AuthenticationService auth;
    @Autowired(required = true)
    private UserRepository userRepo;

    public UserResponse addUser (AddUserRequest request) {
        System.out.println("Dentro do Serviço de cadastro de user ");
        UserResponse response = new UserResponse();
        
        /*
            * Verificando se o utilizador já existe
            */
        UserModel user = userRepo.findByEmail(request.getEmail());
        if (user != null) {
            response.setEstado(false);
            response.setMensagem("Utilizador já existe, tente outro email ou outro Email ou BI!!!");
            return response;
        }
        /*
            * Fim da verificação
            */
        UserModel userModel = new UserModel();
        userModel.setEmail(request.getEmail());
        userModel.setGenero(request.getGenero());
        userModel.setNome(request.getNome());
        userModel.setTipo(request.getTipo());
        userModel.setTelefone(request.getTelefone());
        userModel.setPassword(HashPassword.hashing(request.getPassword()));
        userModel = userRepo.save(userModel);

        response.setEstado(true);
        response.setMensagem("Utilizador adicionado com sucesso!!!");

        return response;
    }

    public UserResponse getUser (GetUserRequest request) {

        UserResponse response = new UserResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            // UserModel userOp = userRepo.findByEmail(request.getBody().getEmail());
            Optional<UserModel> userOp = userRepo.findById(request.getBody().getId());
                System.out.println(userOp.isPresent());
            if (userOp.isPresent()) {
                UserModel userModel = userOp.get();
                BeanUtils.copyProperties(userModel, response);
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public UserListResponse getAllUsers (AllUsersRequest request) {
        UserListResponse response = new UserListResponse();
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else{
            List<UserModel> userList = userRepo.findAll();
            if (!userList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
                userList.forEach(
                    user -> {
                        UserType userType = new UserType();
                        BeanUtils.copyProperties(user, userType);
                        response.getUser().add(userType);
                    });
            } else {
                response.setEstado(false);
                response.setMensagem("Nenhum user Registrado!");
            }
        }
        return response;
    }

}
