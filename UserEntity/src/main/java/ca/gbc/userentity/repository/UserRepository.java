package ca.gbc.userentity.repository;

import ca.gbc.userentity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}


// this is the middleware for database access & control. we can extend database file or connection like MongoDB or
// JpaEntity to apply the database usage


