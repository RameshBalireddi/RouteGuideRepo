package routeGuide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routeGuide.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);

    User findByEmail(String email);
}
