package DAMAnunciosLocService.AnunciosLoc.service;

public package DAMAnunciosLocService.AnunciosLoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAMAnunciosLocService.AnunciosLoc.BD.infraestrutura.InfraestruturaModel;
import DAMAnunciosLocService.AnunciosLoc.BD.infraestrutura.InfraestruturaRepository;
import xml.soap.infraestrutura.*;

@Service
public class InfraestruturaService {

    @Autowired
    private InfraestruturaRepository infraRepo;

    public InfraestruturaResponse addInfraestrutura(AddInfraestruturaRequest request) {
        InfraestruturaResponse response = new InfraestruturaResponse();

        InfraestruturaModel infra = new InfraestruturaModel();
        BeanUtils.copyProperties(request.getBody(), infra);

        infraRepo.save(infra);

        response.setEstado(true);
        response.setMensagem("Infraestrutura adicionada com sucesso!");
        return response;
    }

    public InfraestruturaResponse getInfraestrutura(GetInfraestruturaRequest request) {
        InfraestruturaResponse response = new InfraestruturaResponse();

        Optional<InfraestruturaModel> infraOp = infraRepo.findById(request.getBody().getId());
        if (infraOp.isPresent()) {
            BeanUtils.copyProperties(infraOp.get(), response);
            response.setEstado(true);
            response.setMensagem("Infraestrutura encontrada!");
        } else {
            response.setEstado(false);
            response.setMensagem("Infraestrutura não encontrada!");
        }

        return response;
    }

    public InfraestruturaListResponse getAllInfraestruturas(AllInfraestruturasRequest request) {
        InfraestruturaListResponse response = new InfraestruturaListResponse();
        List<InfraestruturaModel> lista = infraRepo.findAll();

        if (!lista.isEmpty()) {
            response.setEstado(true);
            response.setMensagem("Infraestruturas encontradas!");

            lista.forEach(infra -> {
                InfraestruturaType type = new InfraestruturaType();
                BeanUtils.copyProperties(infra, type);
                response.getInfraestrutura().add(type);
            });
        } else {
            response.setEstado(false);
            response.setMensagem("Nenhuma infraestrutura encontrada!");
        }

        return response;
    }

    public InfraestruturaResponse deleteInfraestrutura(DeleteInfraestruturaRequest request) {
        InfraestruturaResponse response = new InfraestruturaResponse();

        if (infraRepo.existsById(request.getBody().getId())) {
            infraRepo.deleteById(request.getBody().getId());
            response.setEstado(true);
            response.setMensagem("Infraestrutura removida com sucesso!");
        } else {
            response.setEstado(false);
            response.setMensagem("Infraestrutura não encontrada.");
        }

        return response;
    }
}
 InfraestrturaService {
    
}
