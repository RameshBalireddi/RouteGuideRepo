package routeGuide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routeGuide.entities.Load;
@Repository
public interface LoadRepository extends JpaRepository<Load,Integer> {
}
