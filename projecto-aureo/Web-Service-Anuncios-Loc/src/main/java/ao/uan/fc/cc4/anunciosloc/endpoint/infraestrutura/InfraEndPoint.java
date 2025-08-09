package ao.uan.fc.cc4.anunciosloc.endpoint.infraestrutura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.anunciosloc.endpoint.infraestrutura.service.InfraService;
import xml.soap.infra.*;

@Endpoint
@Component
public class InfraEndPoint {

    private static final String NAMESPACE_URI = "http://infra.soap.xml";

    @Autowired(required = true)
    private InfraService infraService;

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "TestInfraRequest")
	@ResponsePayload
    public TestInfraResponse testInfra (@RequestPayload TestInfraRequest request) {
        System.out.println("Entrando no serviço de checkin de estação");
		return infraService.testInfra(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetInfraDetailsRequest")
	@ResponsePayload
    public GetInfraDetailsResponse getInfra (@RequestPayload GetInfraDetailsRequest request) {
        System.out.println("Entrando no serviço get Station");
		return infraService.getInfra(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddInfraRequest")
	@ResponsePayload
    public InfraResponse addInfra (@RequestPayload AddInfraRequest request) {
        System.out.println("Entrando no serviço add Station");
		return infraService.addInfra(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AllInfraRequest")
    @ResponsePayload
    public AllInfraResponse getTodasInfras (@RequestPayload AllInfraRequest request) {
        System.out.println("Entrando no serviço all Station");
        return infraService.getTodasInfras(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AllInfraMoreProximeRequest")
    @ResponsePayload
    public AllInfraResponse getTodasInfrasProximas (@RequestPayload AllInfraMoreProximeRequest request) {
        System.out.println("Entrando no serviço all Station");
        return infraService.getTodasInfrasProximas(request);
    }

}
