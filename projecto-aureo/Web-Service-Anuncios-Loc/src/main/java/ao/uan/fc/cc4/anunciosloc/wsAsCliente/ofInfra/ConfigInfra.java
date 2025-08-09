package ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofInfra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ConfigInfra {

    @Value("${station.uri.port}")
    private String stationPort;

    @Bean
    public Jaxb2Marshaller marshaller () {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setContextPath("xml.soap");

        return marshaller;

    }

    @Bean
    public WSInfra infraClient (Jaxb2Marshaller marshaller) {

        WSInfra client = new WSInfra();
        String baseUrl = "http://127.0.0.1:";
        client.setDefaultUri(baseUrl + stationPort + "/wsInfra");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

}
