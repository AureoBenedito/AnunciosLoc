package DAMAnunciosLocService.AnunciosLoc.controller;

public package DAMAnunciosLocService.AnunciosLoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import DAMAnunciosLocService.AnunciosLoc.service.AnuncioService;
import xml.soap.anuncio.AddAnuncioRequest;
import xml.soap.anuncio.AllAnunciosRequest;
import xml.soap.anuncio.GetAnuncioRequest;
import xml.soap.anuncio.AnuncioListResponse;
import xml.soap.anuncio.AnuncioResponse;

@Endpoint
@Component
public class AnuncioController {

    private static final String NAMESPACE_URI = "http://anuncio.soap.xml";

    @Autowired
    private AnuncioService anuncioService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddAnuncioRequest")
    @ResponsePayload
    public AnuncioResponse addAnuncio(@RequestPayload AddAnuncioRequest request) {
        System.out.println("Entrando no serviço addAnuncio");
        return anuncioService.addAnuncio(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAnuncioRequest")
    @ResponsePayload
    public AnuncioResponse getAnuncio(@RequestPayload GetAnuncioRequest request) {
        return anuncioService.getAnuncio(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllAnunciosRequest")
    @ResponsePayload
    public AnuncioListResponse getAllAnuncios(@RequestPayload AllAnunciosRequest request) {
        return anuncioService.getAllAnuncios(request);
    }
}
 AnuncioController {
    
}
