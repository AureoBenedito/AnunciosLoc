package DAMAnunciosLocService.AnunciosLoc.BD.sessao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends JpaRepository<SessaoModel, Long> {

    SessaoModel findByToken(String token);
    SessaoModel findByFingerprint(String fingerprint);
    SessaoModel findByUser(Long user);

}
