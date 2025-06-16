package com.realestate.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.crm.model.SampleData;

@Repository
public interface SampleDataRepository extends JpaRepository<SampleData, Long>{

}
