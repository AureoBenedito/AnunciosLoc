package ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofJuddi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraRepository;
import ao.uan.fc.dam.ws.uddi.UDDINaming;

@Component
public class JuddiService {

    @Autowired(required = true)
    private InfraRepository stationRepo;
    private UDDINaming uddiNaming;

    public JuddiService () throws Exception{
        uddiNaming = new UDDINaming("http://localhost:9090");
    }

    public List<String> pegaTodasInfras () {
        List<String> servicos = new ArrayList<String>();

        int i = (stationRepo.findAll().size() * 3)/2;
        if (i==0) i = 10;
        try {
            while (i > 0) {
                String urlService = uddiNaming.lookup("CXX_INFRAESTRUTURA" + i);
                if (urlService != null) {
                    System.out.println(urlService);
                    servicos.add(urlService);
                }
                i--;
            }
            if (servicos.isEmpty()) servicos = null;
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviços indisponíveis!!!");
            return null;
        }
        
        return servicos;
    }

    public String pegaInfra (int id) {
        String urlService = null;
        try {
            urlService = uddiNaming.lookup("CXX_INFRAESTRUTURA" + id);
            if (urlService != null) {
                System.out.println(urlService);
                return urlService;
            }
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviço indisponíveis!!!");
            return null;
        }
        return urlService;
    }

    public String pegaInfra (String name) {
        String urlService = null;
        try {
            urlService = uddiNaming.lookup(name);
            if (urlService != null) {
                System.out.println(urlService);
                return urlService;
            }
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviço indisponíveis!!!");
            return null;
        }
        return urlService;
    }
    
}
