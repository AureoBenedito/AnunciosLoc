package ao.uan.fc.cc4.anunciosloc.bd.anuncio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends  JpaRepository<AnuncioModel, Long>{

}