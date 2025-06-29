package com.realestate.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.crm.model.MetaLead;

@Repository
public interface MetaLeadRepository extends JpaRepository<MetaLead, Long>{
	boolean existsByLeadId(String leadId);

}
