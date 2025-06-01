package com.example.castells_cap.repositories;

import com.example.castells_cap.models.Cap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapRepository extends JpaRepository<Cap, Long> {
    Cap findCapByName(String name);
}
