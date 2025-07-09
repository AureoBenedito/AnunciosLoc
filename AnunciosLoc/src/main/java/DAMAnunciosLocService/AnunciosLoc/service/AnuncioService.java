package DAMAnunciosLocService.AnunciosLoc.service;

public package DAMAnunciosLocService.AnunciosLoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAMAnunciosLocService.AnunciosLoc.BD.anuncio.AnuncioModel;
import DAMAnunciosLocService.AnunciosLoc.BD.anuncio.AnuncioRepository;
import xml.soap.anuncio.*;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepo;

    public AnuncioResponse addAnuncio(AddAnuncioRequest request) {
        AnuncioResponse response = new AnuncioResponse();

        AnuncioModel anuncio = new AnuncioModel();
        BeanUtils.copyProperties(request.getBody(), anuncio);

        anuncioRepo.save(anuncio);

        response.setEstado(true);
        response.setMensagem("Anúncio criado com sucesso!");
        return response;
    }

    public AnuncioResponse getAnuncio(GetAnuncioRequest request) {
        AnuncioResponse response = new AnuncioResponse();

        Optional<AnuncioModel> anuncioOp = anuncioRepo.findById(request.getBody().getId());
        if (anuncioOp.isPresent()) {
            BeanUtils.copyProperties(anuncioOp.get(), response);
            response.setEstado(true);
            response.setMensagem("Anúncio encontrado!");
        } else {
            response.setEstado(false);
            response.setMensagem("Anúncio não encontrado!");
        }

        return response;
    }

    public AnuncioListResponse getAllAnuncios(AllAnunciosRequest request) {
        AnuncioListResponse response = new AnuncioListResponse();
        List<AnuncioModel> lista = anuncioRepo.findAll();

        if (!lista.isEmpty()) {
            response.setEstado(true);
            response.setMensagem("Anúncios encontrados!");

            lista.forEach(anuncio -> {
                AnuncioType type = new AnuncioType();
                BeanUtils.copyProperties(anuncio, type);
                response.getAnuncio().add(type);
            });
        } else {
            response.setEstado(false);
            response.setMensagem("Nenhum anúncio encontrado!");
        }

        return response;
    }

    public AnuncioResponse deleteAnuncio(DeleteAnuncioRequest request) {
        AnuncioResponse response = new AnuncioResponse();

        if (anuncioRepo.existsById(request.getBody().getId())) {
            anuncioRepo.deleteById(request.getBody().getId());
            response.setEstado(true);
            response.setMensagem("Anúncio removido com sucesso!");
        } else {
            response.setEstado(false);
            response.setMensagem("Anúncio não encontrado.");
        }

        return response;
    }
}
AnuncioService {
    
}
