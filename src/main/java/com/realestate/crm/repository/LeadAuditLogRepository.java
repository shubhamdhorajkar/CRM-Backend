package com.realestate.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.crm.model.LeadAuditLog;
import com.realestate.crm.model.Leads;

@Repository
public interface LeadAuditLogRepository extends JpaRepository<LeadAuditLog, Long>{
	
	List<LeadAuditLog> findByLead(Leads lead);

}
