package com.realestate.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.crm.enumerations.DataSource;
import com.realestate.crm.enumerations.LeadStatus;
import com.realestate.crm.model.Leads;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long>{
	
	List<Leads> findBySource(DataSource source);
    List<Leads> findByStatus(LeadStatus status);
    List<Leads> findByAssignee(String assignee);
    boolean existsByMetaLeadId(String metaLeadId);
    List<Leads> findByIsSampleTrue();

    List<Leads> findByNotConnectedTrue();

    List<Leads> findByIsSampleFalseAndNotConnectedFalse();

    long countByIsSampleTrue();

    long countByNotConnectedTrue();

    long countByStatus(LeadStatus status);
}
