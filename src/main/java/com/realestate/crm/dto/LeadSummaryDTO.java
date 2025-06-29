package com.realestate.crm.dto;

import java.util.Map;

import com.realestate.crm.enumerations.LeadStatus;

public class LeadSummaryDTO {
	
	private long sampleData;
    private long notConnected;
    private long totalLeads;
    private Map<LeadStatus, Long> leadStatusCounts;
	public long getSampleData() {
		return sampleData;
	}
	public void setSampleData(long sampleData) {
		this.sampleData = sampleData;
	}
	public long getNotConnected() {
		return notConnected;
	}
	public void setNotConnected(long notConnected) {
		this.notConnected = notConnected;
	}
	public long getTotalLeads() {
		return totalLeads;
	}
	public void setTotalLeads(long totalLeads) {
		this.totalLeads = totalLeads;
	}
	public Map<LeadStatus, Long> getLeadStatusCounts() {
		return leadStatusCounts;
	}
	public void setLeadStatusCounts(Map<LeadStatus, Long> leadStatusCounts) {
		this.leadStatusCounts = leadStatusCounts;
	}

    
}
