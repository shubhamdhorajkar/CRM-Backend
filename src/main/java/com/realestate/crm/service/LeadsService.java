package com.realestate.crm.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.crm.dto.LeadSummaryDTO;
import com.realestate.crm.enumerations.ConstructionStatus;
import com.realestate.crm.enumerations.DataSource;
import com.realestate.crm.enumerations.LeadStatus;
import com.realestate.crm.model.LeadAuditLog;
import com.realestate.crm.model.Leads;
import com.realestate.crm.repository.LeadAuditLogRepository;
import com.realestate.crm.repository.LeadsRepository;

@Service
public class LeadsService {
	
	@Autowired
    private LeadsRepository leadRepository;

    @Autowired
    private LeadAuditLogRepository auditLogRepository;

    public Leads createManualLead(Leads lead, String createdBy) {
        lead.setCreatedAt(LocalDateTime.now());
        lead.setSource(DataSource.MANUAL);
        lead.setStatus(LeadStatus.NEW);
        Leads saved = leadRepository.save(lead);
        createAuditLog(saved, null, LeadStatus.NEW, createdBy, "Manual lead created");
        return saved;
    }

    public int bulkUploadFromExcel(MultipartFile file, String uploadedBy) throws IOException {
        List<Leads> leads = new ArrayList<>();
        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (r == null) continue;
                Leads lead = new Leads();
                lead.setFullName(getCell(r, 0));
                lead.setEmail(getCell(r, 1));
                lead.setPhone(getCell(r, 2));
                lead.setLocationLookingFor(getCell(r, 3));
                lead.setClientAddress(getCell(r, 4));
                lead.setPincode(getCell(r, 5));
                lead.setBudget(new BigDecimal(getCell(r, 6)));
                lead.setAreaSqFt(Double.valueOf(getCell(r, 7)));
                lead.setConstructionStatus(ConstructionStatus.valueOf(getCell(r,8)));
                lead.setSeekingFor(getCell(r,9));
                lead.setSource(DataSource.EXCEL);
                lead.setStatus(LeadStatus.valueOf(getCell(r,10)));
                lead.setSample(Boolean.parseBoolean(getCell(r,11)));
                lead.setNotConnected(Boolean.parseBoolean(getCell(r,12)));
                lead.setCreatedAt(LocalDateTime.now());
                leads.add(lead);
            }
        }
        leadRepository.saveAll(leads);

        for (Leads ld : leads) {
            LeadAuditLog log = new LeadAuditLog(
                null,             // VM will auto-generate ID
                ld,               // pass the actual Leads object
                ld.getStatus(),   // oldStatus = current
                ld.getStatus(),   // newStatus = same for initial log
                uploadedBy,
                "Excel bulk upload",
                LocalDateTime.now(),
                null,
                null
            );
            // Directly call our audit log method
            createAuditLog(ld.getId(), log);
        }

        return leads.size();
    }

    private String getCell(Row r, int idx) {
        Cell c = r.getCell(idx);
        return (c == null ? "" : c.toString().trim());
    }

    public void saveMetaLead(Leads lead, String createdBy) {
        if (leadRepository.existsByMetaLeadId(lead.getMetaLeadId())) return;
        lead.setSource(DataSource.META);
        lead.setStatus(LeadStatus.NEW);
        lead.setCreatedAt(LocalDateTime.now());
        leadRepository.save(lead);
        createAuditLog(lead, null, LeadStatus.NEW, createdBy, "Meta lead fetched");
    }

    public Leads updateLeadStatus(Long leadId, LeadStatus newStatus, String updatedBy, String note, LocalDateTime followUpDate, LocalDateTime siteVisitDate) {
    	Leads lead = leadRepository.findById(leadId).orElseThrow();
        LeadStatus oldStatus = lead.getStatus();
        lead.setStatus(newStatus);
        if (lead.isSample()) {
            lead.setSample(false); // No longer sample once interacted with
        }

        if (newStatus == LeadStatus.LOST && note != null && note.toLowerCase().contains("not connected")) {
            lead.setNotConnected(true);
        }
        if (lead.getAssignee() == null) lead.setAssignee(updatedBy);
        if (followUpDate != null) lead.setNextFollowUpDate(followUpDate);
        if (siteVisitDate != null) lead.setNextSiteVisitDate(siteVisitDate);
        leadRepository.save(lead);
        createAuditLog(lead, oldStatus, newStatus, updatedBy, note, followUpDate, siteVisitDate);
        return lead;
    }

    private void createAuditLog(Leads lead, LeadStatus oldStatus, LeadStatus newStatus, String changedBy, String note) {
        createAuditLog(lead, oldStatus, newStatus, changedBy, note, null, null);
    }

    private void createAuditLog(Leads lead, LeadStatus oldStatus, LeadStatus newStatus, String changedBy, String note, LocalDateTime followUp, LocalDateTime visit) {
        LeadAuditLog log = new LeadAuditLog();
        log.setLead(lead);
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setChangedBy(changedBy);
        log.setActionNote(note);
        log.setActionTime(LocalDateTime.now());
        log.setFollowUpDate(followUp);
        log.setSiteVisitDateTime(visit);
        auditLogRepository.save(log);
    }

    public List<LeadAuditLog> getAuditLogs(Long leadId) {
    	Leads lead = leadRepository.findById(leadId).orElseThrow();
        return auditLogRepository.findByLead(lead);
    }

    public List<Leads> getAllLeads() {
        return leadRepository.findAll();
    }
    
    public LeadSummaryDTO getLeadSummary() {
        LeadSummaryDTO summary = new LeadSummaryDTO();

        summary.setSampleData(leadRepository.countByIsSampleTrue());
        summary.setNotConnected(leadRepository.countByNotConnectedTrue());
        summary.setTotalLeads(leadRepository.count());

        // Count by status
        Map<LeadStatus, Long> statusCounts = new HashMap<>();
        for (LeadStatus status : LeadStatus.values()) {
            long count = leadRepository.countByStatus(status);
            statusCounts.put(status, count);
        }

        summary.setLeadStatusCounts(statusCounts);
        return summary;
    }
    public LeadAuditLog createAuditLog(Long leadId, LeadAuditLog log) {
        Leads lead = leadRepository.findById(leadId)
            .orElseThrow(() -> new RuntimeException("Lead not found with id " + leadId));

        log.setLead(lead);
        log.setActionTime(LocalDateTime.now()); // set current time
        return auditLogRepository.save(log);
    }

    /**
     * Update an existing LeadAuditLog entry.
     */
    public LeadAuditLog updateAuditLog(Long logId, LeadAuditLog updatedLog) {
        LeadAuditLog existingLog = auditLogRepository.findById(logId)
            .orElseThrow(() -> new RuntimeException("Audit log not found with id " + logId));

        existingLog.setOldStatus(updatedLog.getOldStatus());
        existingLog.setNewStatus(updatedLog.getNewStatus());
        existingLog.setChangedBy(updatedLog.getChangedBy());
        existingLog.setActionNote(updatedLog.getActionNote());
        existingLog.setFollowUpDate(updatedLog.getFollowUpDate());
        existingLog.setSiteVisitDateTime(updatedLog.getSiteVisitDateTime());
        existingLog.setActionTime(LocalDateTime.now());

        return auditLogRepository.save(existingLog);
    }

    /**
     * Delete a LeadAuditLog by ID.
     */
    public void deleteAuditLog(Long logId) {
        if (!auditLogRepository.existsById(logId)) {
            throw new RuntimeException("Audit log not found with id " + logId);
        }
        auditLogRepository.deleteById(logId);
    }
    

}
