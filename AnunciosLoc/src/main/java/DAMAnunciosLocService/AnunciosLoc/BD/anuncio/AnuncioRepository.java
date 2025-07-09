package DAMAnunciosLocService.AnunciosLoc.BD.anuncio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<AnuncioModel, Long>{
    AnuncioModel findByUser (Long user);
}
