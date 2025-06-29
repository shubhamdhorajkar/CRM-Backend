package com.realestate.crm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.realestate.crm.enumerations.LeadStatus;

@Entity
public class LeadAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Leads lead;

    @Enumerated(EnumType.STRING)
    private LeadStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private LeadStatus newStatus;

    private String changedBy;       // User email or name
    private String actionNote;      // e.g., "Marked as HOT", "Scheduled site visit"
    private LocalDateTime actionTime = LocalDateTime.now();

    // Optional details (only for certain actions)
    private LocalDateTime followUpDate;
    private LocalDateTime siteVisitDateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Leads getLead() {
		return lead;
	}
	public void setLead(Leads lead) {
		this.lead = lead;
	}
	public LeadStatus getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(LeadStatus oldStatus) {
		this.oldStatus = oldStatus;
	}
	public LeadStatus getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(LeadStatus newStatus) {
		this.newStatus = newStatus;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public String getActionNote() {
		return actionNote;
	}
	public void setActionNote(String actionNote) {
		this.actionNote = actionNote;
	}
	public LocalDateTime getActionTime() {
		return actionTime;
	}
	public void setActionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}
	public LocalDateTime getFollowUpDate() {
		return followUpDate;
	}
	public void setFollowUpDate(LocalDateTime followUpDate) {
		this.followUpDate = followUpDate;
	}
	public LocalDateTime getSiteVisitDateTime() {
		return siteVisitDateTime;
	}
	public void setSiteVisitDateTime(LocalDateTime siteVisitDateTime) {
		this.siteVisitDateTime = siteVisitDateTime;
	}
	public LeadAuditLog() {
	    // no-arg constructor required for JPA
	}
	public LeadAuditLog(Long id, Leads lead, LeadStatus oldStatus, LeadStatus newStatus, String changedBy,
			String actionNote, LocalDateTime actionTime, LocalDateTime followUpDate, LocalDateTime siteVisitDateTime) {
		super();
		this.id = id;
		this.lead = lead;
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		this.changedBy = changedBy;
		this.actionNote = actionNote;
		this.actionTime = actionTime;
		this.followUpDate = followUpDate;
		this.siteVisitDateTime = siteVisitDateTime;
	}

    // Getters & setters
    
}
