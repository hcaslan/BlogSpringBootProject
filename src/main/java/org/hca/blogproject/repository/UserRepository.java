package org.hca.blogproject.repository;

import org.hca.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long userId);
}
