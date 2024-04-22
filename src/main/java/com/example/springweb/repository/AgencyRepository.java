package com.example.springweb.repository;

import com.example.springweb.model.entities.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

}
