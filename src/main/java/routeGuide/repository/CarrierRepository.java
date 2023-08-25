package routeGuide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routeGuide.entities.Carrier;
import routeGuide.entities.Load;

import java.util.Optional;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Integer> {


}
