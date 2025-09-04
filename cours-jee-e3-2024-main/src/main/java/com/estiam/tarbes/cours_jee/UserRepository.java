package com.estiam.tarbes.cours_jee;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);
    
    Optional<User> findByEmail(String email);
    User findByName(String name);
}
