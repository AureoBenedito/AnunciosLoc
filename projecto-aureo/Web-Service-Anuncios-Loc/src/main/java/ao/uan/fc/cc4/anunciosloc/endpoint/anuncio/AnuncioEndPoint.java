package ao.uan.fc.cc4.anunciosloc.endpoint.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.anunciosloc.endpoint.anuncio.service.AnuncioService;
import xml.soap.anuncio.AddAnuncioRequest;
import xml.soap.anuncio.AddAnuncioResponse;
import xml.soap.anuncio.AllAnunciosProximosRequest;
import xml.soap.anuncio.AllAnunciosRequest;
import xml.soap.anuncio.DeleteAnuncioRequest;
import xml.soap.anuncio.DeleteAnuncioResponse;
import xml.soap.anuncio.GetAnuncioRequest;
import xml.soap.anuncio.GetAnuncioResponse;
import xml.soap.anuncio.ListAnuncioResponse;

@Endpoint
@Component
public class AnuncioEndPoint {
	
	private static final String NAMESPACE_URI = "http://anuncio.soap.xml";
	
	@Autowired(required = true)
	private AnuncioService anuncioService; 

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddAnuncioRequest")
	@ResponsePayload
	public AddAnuncioResponse AddAnuncio (@RequestPayload AddAnuncioRequest request) {
		System.out.println("Entrando no serviço de anuncios");
		return anuncioService.addAnuncio(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAnuncioRequest")
	@ResponsePayload
	public GetAnuncioResponse getAnuncio (@RequestPayload GetAnuncioRequest request) {
		System.out.println("Entrando no serviço de anuncios");
		return anuncioService.getAnuncio(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteAnuncioRequest")
	@ResponsePayload
	public DeleteAnuncioResponse deleteAnuncio (@RequestPayload DeleteAnuncioRequest request) {
		System.out.println("Entrando no serviço de anuncios");
		return anuncioService.deleteAnuncio(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllAnunciosRequest")
	@ResponsePayload
	public ListAnuncioResponse getAllAnuncios (@RequestPayload AllAnunciosRequest request) {
		System.out.println("Entrando no serviço de anuncios");
		return anuncioService.getAllAnuncios(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllAnunciosProximosRequest")
	@ResponsePayload
	public ListAnuncioResponse getAllAnunciosProximos (@RequestPayload AllAnunciosProximosRequest request) {
		System.out.println("Entrando no serviço de anuncios");
		return anuncioService.getAllAnunciosProximos(request);
	}

}