package DAMAnunciosLocService.AnunciosLoc.BD.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    ClienteModel findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
