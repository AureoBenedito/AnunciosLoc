package ao.uan.fc.cc4.anunciosloc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraModel;
import ao.uan.fc.cc4.anunciosloc.bd.infra.InfraRepository;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofJuddi.JuddiService;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ao.uan.fc.cc4.anunciosloc.wsAsCliente.ofInfra.WSInfra;
import xml.soap.GetInfraResponse;

@SpringBootApplication
public class AnunciosLocApplication {

	@Autowired(required = true)
	JuddiService juddiService;
    @Autowired(required = true)
    private InfraRepository infraRepo;
	@Autowired
    private WSInfra infraClient;

	public static void main(String[] args) {

		SpringApplication.run(AnunciosLocApplication.class, args);
		System.out.println("Web Service Anuncios Loc Esta Activo!!!");
		System.out.println("http://localhost:8080/ws/infra.wsdl");
		System.out.println("http://localhost:8080/ws/user.wsdl");
		System.out.println("http://localhost:8080/ws/anuncio.wsdl");
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner executar () {
		return args -> {
			GetInfraResponse gsr = null;
			InfraModel infra = null;
			boolean pmr = false; //esta variável confirma que pelo menos uma Infrastrurtura foi encontrada no uddi e registrada
			List<String> servicos = juddiService.pegaTodasInfras();
			if (servicos != null) {
				for(String servico : servicos){
					System.out.println();
					System.out.println(servico);
					System.out.println();
					try{
						gsr = infraClient.getInfraData(servico);
						if (gsr != null) {
							if (infraRepo.existsByEndpoint(servico)) {
								infra = infraRepo.findByEndpoint(servico);
							} else {
								pmr = true;
								infra = new InfraModel();
								infra.setEndpoint(servico);
							}
							infra.setBonus(gsr.getBonus());
							infra.setLatitude(gsr.getLatitude());
							infra.setLongitude(gsr.getLongitude());
							// infra.setLocalName(gsr.getLocalName());
							infra.setName(gsr.getName());
							infra.setState(1);
							
							try{
								infraRepo.save(infra);
							} catch (Exception exc) {
								System.out.println("Não deu para salvar o registro!!!");
							}

							infraRepo.save(infra);
						} else {
							System.out.println("Infrastrurtura inactiva!!!");
						}
					} catch (Exception e) {
						System.out.println("Infrastrurtura inactiva!!!");
					}
				}
				if (pmr) {
					System.out.println("Novas infrastruturas foram encontradas e adicionadas!!!");
				} else {
					System.out.println("Existem infrastruturas disponíveis!!!");
				}
			} else {
				System.out.println("Serviços de infrastruturas indisponíveis");
			}
		};
	}

}