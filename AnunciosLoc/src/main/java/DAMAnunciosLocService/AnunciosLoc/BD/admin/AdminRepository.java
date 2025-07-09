package DAMAnunciosLocService.AnunciosLoc.BD.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long>{
    AdminModel findByUserId (Long userId);
}
