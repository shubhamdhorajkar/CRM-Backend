package com.realestate.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.crm.model.Leads;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long>{
	
	@Query("SELECT COUNT(l) FROM Leads l WHERE DATE(l.createdAt) = CURRENT_DATE")
	long getTotalLeadsToday();
	
	@Query("SELECT COUNT(l) FROM Leads l WHERE l.status = 'CLOSED' AND MONTH(l.closedAt) = MONTH(CURRENT_DATE)")
	long getClosedDealsThisMonth();

}
