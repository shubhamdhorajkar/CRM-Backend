package com.realestate.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.crm.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{

	@Query("SELECT COUNT(p) FROM Property p")
    long getTotalPropertiesListed();
}
