package com.realestate.crm.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Leads {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstVisit;  // e.g., "Site Visit", "Revisit"
    private LocalDate siteVisitDate;
    private String contactName;
    private String contact;
    private String email;
    private String nationality;
    private String ageGroup;
    private String ethnicity;
    private String employmentType;
    private String companyName;
    private String officeLocality;
    private String pincode;
    private String industry;
    private String address1;
    private String locality;
    private String unitType;
    private String budget;
    private String areaSqFeet;
    private String constructionStatus;
    private String seekingFor;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt; // Stores the lead creation time

    @Temporal(TemporalType.TIMESTAMP)
    private Date closedAt; // Stores when the lead was closed (nullable)

    private String status;
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date(); // Auto-set creation time
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstVisit() {
		return firstVisit;
	}
	public void setFirstVisit(String firstVisit) {
		this.firstVisit = firstVisit;
	}
	public LocalDate getSiteVisitDate() {
		return siteVisitDate;
	}
	public void setSiteVisitDate(LocalDate siteVisitDate) {
		this.siteVisitDate = siteVisitDate;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOfficeLocality() {
		return officeLocality;
	}
	public void setOfficeLocality(String officeLocality) {
		this.officeLocality = officeLocality;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getAreaSqFeet() {
		return areaSqFeet;
	}
	public void setAreaSqFeet(String areaSqFeet) {
		this.areaSqFeet = areaSqFeet;
	}
	public String getConstructionStatus() {
		return constructionStatus;
	}
	public void setConstructionStatus(String constructionStatus) {
		this.constructionStatus = constructionStatus;
	}
	public String getSeekingFor() {
		return seekingFor;
	}
	public void setSeekingFor(String seekingFor) {
		this.seekingFor = seekingFor;
	}
    
    
}
