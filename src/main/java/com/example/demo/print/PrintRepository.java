package com.example.demo.print;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;


public interface PrintRepository extends JpaRepository<Print, Long> {

    Optional<Print> findByName(String name);
}

