package ao.uan.fc.cc4.anunciosloc.endpoint.infraestrutura.service;


import java.util.List;
import java.util.Optional;

import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraModel;
import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.anunciosloc.endpoint.user.service.AuthenticationService;
import ao.uan.fc.cc4.anunciosloc.utils.Coordinates;
import ao.uan.fc.cc4.anunciosloc.utils.GeoLocationDTO;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofInfra.WSInfra;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofJOpenCage.ServiceGeoLocation;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofJuddi.JuddiService;
import xml.soap.infra.*;
import xml.soap.GetInfraResponse;


@Service
public class InfraService {

    @Autowired
    private WSInfra infraClient;
    @Autowired
    private AuthenticationService auth;
    @Autowired(required = true)
    private InfraRepository infraRepo;
    @Autowired(required = true)
    private ServiceGeoLocation geoLocationService;
    @Autowired
    private JuddiService juddiService;

    public TestInfraResponse testInfra (TestInfraRequest request) {

        System.out.println("Dentro do Serviço de checkin de estação: " + request.getBody().getId());
        TestInfraResponse response = new TestInfraResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<InfraModel> infra = infraRepo.findById(request.getBody().getId());
            if(infra.isPresent()) {
                try{
                    GetInfraResponse gsr = infraClient.getInfraData(infra.get().getEndpoint());
                    if (gsr != null) {
                        infra.get().setState(1);
                        infraRepo.save(infra.get());
                        response.setEstado(true);
                        response.setMensagem("Estação activa!!!");
                    } else {
                        infra.get().setState(0);
                        infraRepo.save(infra.get()); 
                        response.setEstado(false);
                        response.setMensagem("Estação inactiva!!!");
                    }
                } catch (Exception e) {
                    infra.get().setState(0);
                    infraRepo.save(infra.get());
                    response.setEstado(false);
                    response.setMensagem("Estação inactiva!!!");
                }
            } else {
                response.setEstado(false);
                response.setMensagem("Estação não foi encontrada");
            }
        }
        return response;
    }

    public InfraResponse addInfra (AddInfraRequest request) {
        System.out.println("Dentro do Serviço de registro de novas estações!!! ");
        InfraResponse response = new InfraResponse();
        GetInfraResponse gsr = null;
        InfraModel infra = null;
        boolean pmr = false; //esta variável confirma que pelo menos uma estação foi encontrada no uddi e registrada
        List<String> servicos = juddiService.pegaTodasInfras();
        //List<String> servicos = new ArrayList<>();
        if (servicos != null) {
            for(String servico : servicos){
                try{
                    gsr = infraClient.getInfraData(servico);
                    if (gsr != null) {
                        response.setEstado(true);
                        System.out.println(gsr.getName());
                        if (infraRepo.existsByEndpoint(servico)) {
                            infra = infraRepo.findByEndpoint(servico);
                        } else {
                            pmr = true;
                            System.out.println("ok2x");
                            infra = new InfraModel();
                            infra.setEndpoint(servico);
                        }
                        infra.setBonus(gsr.getBonus());
                        infra.setLatitude(gsr.getLatitude());
                        infra.setLongitude(gsr.getLongitude());
                        infra.setLocalName(gsr.getName());
                        infra.setName(gsr.getName());
                        infra.setState(1);

                        try{
                            infra = infraRepo.save(infra);
                        } catch (Exception exc) {
                            System.out.println("Não deu para salvar o registro!!!");
                        }

                    } else {
                        System.out.println("Infraestrutura inactiva!!!");
                    }
                } catch (Exception e) {
                    System.out.println("Infraestrutura inactiva!!!");
                }
            }
            if (pmr) {
                response.setMensagem("Novas infraestruturas foram encontradas e adicionadas!!!");
            } else {
                response.setMensagem("Não há infraestruturas");
            }
        }else {
            response.setMensagem("Serviços de estações indisponíveis");
        }
        return response;
    }

    public GetInfraDetailsResponse getInfra (GetInfraDetailsRequest request) {

        System.out.println("Dentro do Serviço e ID da estação: " + request.getBody().getId());
        GetInfraDetailsResponse response = new GetInfraDetailsResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<InfraModel> infraModel = infraRepo.findById(request.getBody().getId());
            if(infraModel.isPresent()) {

                System.out.println(infraModel.get().getEndpoint());
                GetInfraResponse gsr = infraClient.getInfraData(infraModel.get().getEndpoint());

                if (gsr != null) {

                    infraRepo.save(infraModel.get());

                    BeanUtils.copyProperties(gsr, response);
                    response.setEstado(true);
                    response.setMensagem("infra encontrada com sucesso!!!");

                    GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(infraModel.get().getLatitude()), String.valueOf(infraModel.get().getLongitude()));

                    if(geoLocation!=null){

                        System.out.println("País: "+geoLocation.getPais());
                        System.out.println("Província: "+geoLocation.getProvincia());
                        System.out.println("Município: "+geoLocation.getMunicipio());
                        System.out.println("Distrito: "+geoLocation.getDistrito());
                        System.out.println("Avenida: "+geoLocation.getAvenida());
        
                        response.setPais(geoLocation.getPais());
                        response.setProvincia(geoLocation.getProvincia());
                        response.setMunicipio(geoLocation.getMunicipio());
                        response.setDistrito(geoLocation.getDistrito());
                        response.setAvenida(geoLocation.getAvenida());
                    }

                    return response;
                }
                response.setEstado(false);
                response.setMensagem("Infra data corrupted!!!");
                return response;
            }else {
                response.setEstado(false);
                response.setMensagem("Infra não foi encontrada");
            }
        }
        return response;
    }

    public AllInfraResponse getTodasInfras (AllInfraRequest request) {
        System.out.println(request.getHeader().getAuthToken());
        AllInfraResponse response = new AllInfraResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            List<InfraModel> infraList = infraRepo.findAll();
            if (!infraList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Infras encontradas com sucesso!");
                for(InfraModel infra : infraList) {

                    if(juddiService.pegaInfra(infra.getName())!=null){
                    //if(infra.getName() != null){

                        GetInfraResponse gsr = infraClient.getInfraData(infra.getEndpoint());

                        if (gsr != null) {
                            InfraType infraType = new InfraType();
                            // BeanUtils.copyProperties(infra, infraType);
                            infraType.setLatitude(gsr.getLatitude());
                            infraType.setBonus(gsr.getBonus());
                            infraType.setLatitude(gsr.getLatitude());
                            infraType.setLongitude(gsr.getLongitude());
                            infraType.setLocalName(gsr.getName());
                            infraType.setName(gsr.getName());
        
                            GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(infra.getLatitude()), String.valueOf(infra.getLongitude()));
        
                            if(geoLocation!=null){
                                infraType.setPais(geoLocation.getPais());
                                infraType.setProvincia(geoLocation.getProvincia());
                                infraType.setMunicipio(geoLocation.getMunicipio());
                                infraType.setDistrito(geoLocation.getDistrito());
                                infraType.setAvenida(geoLocation.getAvenida());
                            }
                            response.getInfra().add(infraType);
                        }
                    }
                }
            } else {
                response.setEstado(false);
                response.setMensagem("Não há infras ainda!");
            }
        }
        return response;
    }

    public AllInfraResponse getTodasInfrasProximas (AllInfraMoreProximeRequest request) {
        AllInfraResponse response = new AllInfraResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            List<InfraModel> infraList = infraRepo.findAll();
            if (!infraList.isEmpty()) {
                response.setEstado(true);
                infraList.forEach( infra -> {
                    if(juddiService.pegaInfra(infra.getName())!=null){
                    //if(infra.getName() != null){

                        GetInfraResponse gsr = infraClient.getInfraData(infra.getEndpoint());

                        if (gsr != null) {
                            Float distancia = geoLocationService.
                                calcDistanceHaversine(
                                    new Coordinates(request.getBody().getLatitude(), request.getBody().getLongitude()), 
                                    new Coordinates(gsr.getLatitude(), gsr.getLongitude()), 
                                    gsr.getRaio()
                                );
                            
                            if(distancia>0){
                                InfraType infraType = new InfraType();
                                // BeanUtils.copyProperties(infra, infraType);
                                infraType.setLatitude(gsr.getLatitude());
                                infraType.setBonus(gsr.getBonus());
                                infraType.setLatitude(gsr.getLatitude());
                                infraType.setLongitude(gsr.getLongitude());
                                infraType.setLocalName(gsr.getName());
                                infraType.setName(gsr.getName());
                                infraType.setDistancia(distancia.intValue());

                                GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(infra.getLatitude()), String.valueOf(infra.getLongitude()));

                                if(geoLocation!=null){
                                    infraType.setPais(geoLocation.getPais());
                                    infraType.setProvincia(geoLocation.getProvincia());
                                    infraType.setMunicipio(geoLocation.getMunicipio());
                                    infraType.setDistrito(geoLocation.getDistrito());
                                    infraType.setAvenida(geoLocation.getAvenida());
                                }
                                response.getInfra().add(infraType);
                            }
                        }
                    }
                });
                if (response.getInfra().isEmpty()) response.setMensagem("Não há estações num raio de "+request.getBody().getRadius()+" metros");
            } else {
                response.setEstado(false);
                response.setMensagem("Não há infras no sistema ainda!");
            }
        }
        return response;
    }

}
