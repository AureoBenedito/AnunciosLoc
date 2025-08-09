package ao.uan.fc.cc4.anunciosloc.bd.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfraRepository extends JpaRepository<InfraModel, Long> {

    InfraModel findByEndpoint (String endpoint);
    InfraModel findByName(String name);
    boolean existsByName(String name);
    boolean existsByEndpoint(String enpoint);

}
