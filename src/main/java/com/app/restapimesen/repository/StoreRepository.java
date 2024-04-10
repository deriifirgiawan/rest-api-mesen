package com.app.restapimesen.repository;

import com.app.restapimesen.entity.stores.Stores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Stores, String> {
    Optional<Stores> findStoreByName(String name);
}
