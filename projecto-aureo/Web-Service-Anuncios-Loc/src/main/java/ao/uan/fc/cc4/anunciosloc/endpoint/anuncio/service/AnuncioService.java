package ao.uan.fc.cc4.anunciosloc.endpoint.anuncio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.anunciosloc.bd.anuncio.AnuncioModel;
import ao.uan.fc.cc4.anunciosloc.bd.anuncio.AnuncioRepository;
import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraModel;
import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraRepository;
import ao.uan.fc.cc4.anunciosloc.bd.user.UserModel;
import ao.uan.fc.cc4.anunciosloc.bd.user.UserRepository;
import ao.uan.fc.cc4.anunciosloc.endpoint.user.service.AuthenticationService;
import ao.uan.fc.cc4.anunciosloc.utils.Coordinates;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofInfra.WSInfra;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofJOpenCage.ServiceGeoLocation;
import xml.soap.GetInfraResponse;
import xml.soap.anuncio.AddAnuncioRequest;
import xml.soap.anuncio.AddAnuncioResponse;
import xml.soap.anuncio.AllAnunciosProximosRequest;
import xml.soap.anuncio.AllAnunciosRequest;
import xml.soap.anuncio.AnuncioType;
import xml.soap.anuncio.DeleteAnuncioRequest;
import xml.soap.anuncio.DeleteAnuncioResponse;
import xml.soap.anuncio.GetAnuncioRequest;
import xml.soap.anuncio.GetAnuncioResponse;
import xml.soap.anuncio.ListAnuncioResponse;

@Service
public class AnuncioService{

    @Autowired(required = true)
    private AnuncioRepository anuncioRepo;
    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private InfraRepository infraRepo;
    @Autowired
    private AuthenticationService auth;
    @Autowired(required = true)
    private ServiceGeoLocation geoLocationService;
    @Autowired
    private WSInfra infraClient;
    
    public AddAnuncioResponse addAnuncio (AddAnuncioRequest request) {
        AddAnuncioResponse response = new AddAnuncioResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setState(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            if(!userRepo.existsById(request.getBody().getAutor())){
                response.setState(false);
                response.setMensagem("autor nao existe");
            return response;
            }
            if(!infraRepo.existsByName(request.getBody().getLocal())){
                response.setState(false);
                response.setMensagem("local nao existe!");
                return response;
            }
            AnuncioModel anuncio = new AnuncioModel();
            BeanUtils.copyProperties(request.getBody(), anuncio);

            anuncioRepo.save(anuncio);

            response.setState(true);
            response.setMensagem("Anuncio Criado com sucesso.");
        }

        return response;
    }
    
    public GetAnuncioResponse getAnuncio (GetAnuncioRequest request) {
        GetAnuncioResponse response = new GetAnuncioResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<AnuncioModel> anuncio = anuncioRepo.findById(request.getBody().getId());
            if(anuncio.isPresent()){
                // BeanUtils.copyProperties(response, anuncio.get());
                response.setAutor(anuncio.get().getAutor());
                response.setDescricao(anuncio.get().getDescricao());
                response.setId(anuncio.get().getId());
                response.setLocal(anuncio.get().getLocal());
                response.setTitulo(anuncio.get().getTitulo());

                Optional<UserModel> user = userRepo.findById(anuncio.get().getAutor());
                response.setNomeAutor(user.get().getNome());
                response.setTelefoneAutor(user.get().getTelefone());

                InfraModel infra = infraRepo.findByName(anuncio.get().getLocal());

                GetInfraResponse gsr = infraClient.getInfraData(infra.getEndpoint());

                if (gsr != null) {
                    response.setLocalName(infra.getLocalName());
                    response.setLatitude(gsr.getLatitude());
                    response.setLongitude(gsr.getLongitude());
                    response.setRaio(gsr.getRaio());
                }

                response.setEstado(true);
                response.setMensagem("Anuncio encontrado!");
            }else{
            response.setEstado(false);
            response.setMensagem("Anuncio nao encontrado!");
            }
        }

        return response;
    }
    
    public DeleteAnuncioResponse deleteAnuncio (DeleteAnuncioRequest request) {
        DeleteAnuncioResponse response = new DeleteAnuncioResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setState(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<AnuncioModel> anuncio = anuncioRepo.findById(request.getBody().getId());
            if(anuncio.isPresent()){
                anuncioRepo.delete(anuncio.get());
                response.setState(true);
                response.setMensagem("Anuncio eliminado!");
            }else{
                response.setState(false);
                response.setMensagem("Anuncio nao eliminado!");
            }
        }

        return response;
    }
    
    public ListAnuncioResponse getAllAnuncios (AllAnunciosRequest request) {
        ListAnuncioResponse response = new ListAnuncioResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            response.setEstado(true);
            List<AnuncioModel> anuncios = anuncioRepo.findAll();
            anuncios.forEach(anuncio -> {
                AnuncioType anuncioType = new AnuncioType();
                anuncioType.setAutor(anuncio.getAutor());
                anuncioType.setDescricao(anuncio.getDescricao());
                anuncioType.setId(anuncio.getId());
                anuncioType.setLocal(anuncio.getLocal());
                anuncioType.setTitulo(anuncio.getTitulo());

                Optional<UserModel> user = userRepo.findById(anuncio.getAutor());
                anuncioType.setNomeAutor(user.get().getNome());
                anuncioType.setTelefoneAutor(user.get().getTelefone());

                InfraModel infra = infraRepo.findByName(anuncio.getLocal());

                GetInfraResponse gsr = infraClient.getInfraData(infra.getEndpoint());

                if (gsr != null) {
                    anuncioType.setLocalName(infra.getLocalName());
                    anuncioType.setLatitude(gsr.getLatitude());
                    anuncioType.setLongitude(gsr.getLongitude());
                    anuncioType.setRaio(gsr.getRaio());
                }
                
                response.getAnuncio().add(anuncioType);
            });
        }

        return response;
    }
    
    public ListAnuncioResponse getAllAnunciosProximos (AllAnunciosProximosRequest request) {
        ListAnuncioResponse response = new ListAnuncioResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            response.setEstado(true);
            List<AnuncioModel> anuncios = anuncioRepo.findAll();
            anuncios.forEach(anuncio -> {

                Float distancia = -1f;

                AnuncioType anuncioType = new AnuncioType();
                anuncioType.setAutor(anuncio.getAutor());
                anuncioType.setDescricao(anuncio.getDescricao());
                anuncioType.setId(anuncio.getId());
                anuncioType.setLocal(anuncio.getLocal());
                anuncioType.setTitulo(anuncio.getTitulo());

                Optional<UserModel> user = userRepo.findById(anuncio.getAutor());
                anuncioType.setNomeAutor(user.get().getNome());
                anuncioType.setTelefoneAutor(user.get().getTelefone());

                InfraModel infra = infraRepo.findByName(anuncio.getLocal());

                GetInfraResponse gsr = infraClient.getInfraData(infra.getEndpoint());
                if (gsr != null) {
                    anuncioType.setLocalName(infra.getLocalName());
                    anuncioType.setLatitude(gsr.getLatitude());
                    anuncioType.setLongitude(gsr.getLongitude());
                    anuncioType.setRaio(gsr.getRaio());

                    distancia = geoLocationService.
                        calcDistanceHaversine(
                            new Coordinates(request.getBody().getLatitude(), request.getBody().getLongitude()), 
                            new Coordinates(gsr.getLatitude(), gsr.getLongitude()), 
                            gsr.getRaio()
                        );
                    
                    anuncioType.setDistancia(distancia);
                }
                    
                if(distancia>=0){
                    response.getAnuncio().add(anuncioType);
                }
            });
        }

        return response;
    }

}