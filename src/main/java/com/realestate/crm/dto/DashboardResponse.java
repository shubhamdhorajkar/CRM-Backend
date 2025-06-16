package com.realestate.crm.dto;

public class DashboardResponse {
	
	private long totalLeadsToday;
    private long closedDealsThisMonth;
    private long totalPropertiesListed;

    public DashboardResponse(long totalLeadsToday, long closedDealsThisMonth, long totalPropertiesListed) {
        this.totalLeadsToday = totalLeadsToday;
        this.closedDealsThisMonth = closedDealsThisMonth;
        this.totalPropertiesListed = totalPropertiesListed;
    }

    public long getTotalLeadsToday() { return totalLeadsToday; }
    public long getClosedDealsThisMonth() { return closedDealsThisMonth; }
    public long getTotalPropertiesListed() { return totalPropertiesListed; }

}
