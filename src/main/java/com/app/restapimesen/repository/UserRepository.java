package com.app.restapimesen.repository;

import com.app.restapimesen.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findUserByEmail(String email);

    @Query(
            value = "select * from users u where u.store_id = :storeId and u.role_id = 3",
            nativeQuery = true
    )
    List<Users> findUsersByStoreId(@Param("storeId") String store_id);
}
