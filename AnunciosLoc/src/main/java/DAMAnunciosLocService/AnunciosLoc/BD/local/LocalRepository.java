package DAMAnunciosLocService.AnunciosLoc.BD.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<LocalModel, Long>{}
