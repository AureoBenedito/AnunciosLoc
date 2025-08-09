package ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofInfra;

import org.springframework.stereotype.Component;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import xml.soap.GetInfraRequest;
import xml.soap.GetInfraResponse;

@Component
public class WSInfra extends WebServiceGatewaySupport {

    // private static final Logger log = LoggerFactory.getLogger(StationClient.class);

    public GetInfraResponse getInfraData (String url) {
        GetInfraRequest request = new GetInfraRequest();
        System.out.println("Data of INFRAESTRUTURA: ");
        GetInfraResponse response = (GetInfraResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://soap.xml/GetInfraRequest")
            );
        return response;
    }

}
