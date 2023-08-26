package routeGuide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import routeGuide.entities.Load;

import java.util.List;

@Repository
public interface LoadRepository extends JpaRepository<Load,Integer> {

    @Query(value = "select * from loads where user_id=:userId",nativeQuery = true)
    List<Load> findByUserId(@Param(("userId")) int userId);
}
