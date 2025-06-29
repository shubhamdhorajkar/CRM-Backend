package com.realestate.crm.enumerations;

public enum UserRole {
	
	OWNER,              // Platform admin, can see all users
    BUSINESS_HEAD,      // Reports to OWNER
    SENIOR_SALES_MANAGER, // Reports to BUSINESS_HEAD
    SALES_MANAGER,      // Reports to SENIOR_SALES_MANAGER
    SALES_EXECUTIVE     // Reports to SALES_MANAGER

}
