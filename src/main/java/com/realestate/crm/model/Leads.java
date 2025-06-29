package com.realestate.crm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.realestate.crm.enumerations.ConstructionStatus;
import com.realestate.crm.enumerations.DataSource;
import com.realestate.crm.enumerations.LeadStatus;

import java.util.Date;


@Entity
public class Leads {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 // Contact Information
    private String fullName;
    private String email;
    private String phone;

    // Source: MANUAL, EXCEL, META
    @Enumerated(EnumType.STRING)
    private DataSource source;

    // Status: NEW, HOT, LOST, etc.
    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    private String assignee; // User who converted/owns the lead

    // Timestamps
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime nextFollowUpDate;
    private LocalDateTime nextSiteVisitDate;

    // Location & Preferences
    private String locationLookingFor;
    private String clientAddress;
    private String pincode;
    private BigDecimal budget;
    private Double areaSqFt;

    @Enumerated(EnumType.STRING)
    private ConstructionStatus constructionStatus; // UNDER_CONSTRUCTION, READY_TO_MOVE
    private String seekingFor; // 2 BHK, Commercial, etc.

    // Meta Lead ID (optional, if source is META)
    private String metaLeadId;

    // Notes
    private String notes;

    @Column(name = "is_sample")
    private boolean isSample = true; // True by default for new leads

    @Column(name = "not_connected")
    private boolean notConnected = false; // Set true if user tries and fails to reach lead
    
    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeadAuditLog> auditLogs = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}

	public LeadStatus getStatus() {
		return status;
	}

	public void setStatus(LeadStatus status) {
		this.status = status;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getNextFollowUpDate() {
		return nextFollowUpDate;
	}

	public void setNextFollowUpDate(LocalDateTime nextFollowUpDate) {
		this.nextFollowUpDate = nextFollowUpDate;
	}

	public LocalDateTime getNextSiteVisitDate() {
		return nextSiteVisitDate;
	}

	public void setNextSiteVisitDate(LocalDateTime nextSiteVisitDate) {
		this.nextSiteVisitDate = nextSiteVisitDate;
	}

	public String getLocationLookingFor() {
		return locationLookingFor;
	}

	public void setLocationLookingFor(String locationLookingFor) {
		this.locationLookingFor = locationLookingFor;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public Double getAreaSqFt() {
		return areaSqFt;
	}

	public void setAreaSqFt(Double areaSqFt) {
		this.areaSqFt = areaSqFt;
	}

	public ConstructionStatus getConstructionStatus() {
		return constructionStatus;
	}

	public void setConstructionStatus(ConstructionStatus constructionStatus) {
		this.constructionStatus = constructionStatus;
	}

	public String getSeekingFor() {
		return seekingFor;
	}

	public void setSeekingFor(String seekingFor) {
		this.seekingFor = seekingFor;
	}

	public String getMetaLeadId() {
		return metaLeadId;
	}

	public void setMetaLeadId(String metaLeadId) {
		this.metaLeadId = metaLeadId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<LeadAuditLog> getAuditLogs() {
		return auditLogs;
	}

	public void setAuditLogs(List<LeadAuditLog> auditLogs) {
		this.auditLogs = auditLogs;
	}

	public boolean isSample() {
		return isSample;
	}

	public void setSample(boolean isSample) {
		this.isSample = isSample;
	}

	public boolean isNotConnected() {
		return notConnected;
	}

	public void setNotConnected(boolean notConnected) {
		this.notConnected = notConnected;
	}
    
    
    
}
