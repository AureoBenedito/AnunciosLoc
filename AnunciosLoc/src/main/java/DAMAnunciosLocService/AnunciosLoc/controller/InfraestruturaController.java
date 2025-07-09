package DAMAnunciosLocService.AnunciosLoc.controller;

public package DAMAnunciosLocService.AnunciosLoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import DAMAnunciosLocService.AnunciosLoc.service.InfraestruturaService;
import xml.soap.infraestrutura.AddInfraRequest;
import xml.soap.infraestrutura.GetInfraRequest;
import xml.soap.infraestrutura.AllInfraRequest;
import xml.soap.infraestrutura.InfraResponse;
import xml.soap.infraestrutura.InfraListResponse;

@Endpoint
@Component
public class InfraestruturaController {

    private static final String NAMESPACE_URI = "http://infraestrutura.soap.xml";

    @Autowired
    private InfraestruturaService infraestruturaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddInfraRequest")
    @ResponsePayload
    public InfraResponse addInfraestrutura(@RequestPayload AddInfraRequest request) {
        return infraestruturaService.addInfraestrutura(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetInfraRequest")
    @ResponsePayload
    public InfraResponse getInfraestrutura(@RequestPayload GetInfraRequest request) {
        return infraestruturaService.getInfraestrutura(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllInfraRequest")
    @ResponsePayload
    public InfraListResponse getAllInfraestruturas(@RequestPayload AllInfraRequest request) {
        return infraestruturaService.getAllInfraestruturas(request);
    }
}
 InfraestrturasController {
    
}
