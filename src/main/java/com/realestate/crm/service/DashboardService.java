package com.realestate.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.crm.dto.DashboardResponse;
import com.realestate.crm.repository.LeadsRepository;
import com.realestate.crm.repository.PropertyRepository;

@Service
public class DashboardService {

	@Autowired
    private LeadsRepository leadRepository;
	
	@Autowired
    private PropertyRepository propertyRepository;
	
	public DashboardResponse getDashboardData() {
        long totalLeadsToday = leadRepository.getTotalLeadsToday();
        long closedDealsThisMonth = leadRepository.getClosedDealsThisMonth();
        long totalPropertiesListed = propertyRepository.getTotalPropertiesListed();

        return new DashboardResponse(totalLeadsToday, closedDealsThisMonth, totalPropertiesListed);
    }
}
