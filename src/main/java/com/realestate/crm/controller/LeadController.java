package com.realestate.crm.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.crm.dto.LeadSummaryDTO;
import com.realestate.crm.enumerations.LeadStatus;
import com.realestate.crm.model.LeadAuditLog;
import com.realestate.crm.model.Leads;
import com.realestate.crm.model.MetaLead;
import com.realestate.crm.service.LeadsService;
import com.realestate.crm.service.MetaLeadService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
	
	@Autowired
    private LeadsService leadService;

    @PostMapping("/manual")
    public Leads createManualLead(@RequestBody Leads lead, @RequestParam String createdBy) {
        return leadService.createManualLead(lead, createdBy);
    }

    @PostMapping("/meta")
    public void saveMetaLead(@RequestBody Leads lead, @RequestParam String createdBy) {
        leadService.saveMetaLead(lead, createdBy);
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<?> bulkUploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam String uploadedBy) {
        try {
            int count = leadService.bulkUploadFromExcel(file, uploadedBy);
            return ResponseEntity.ok("Uploaded " + count + " leads successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Leads updateStatus(@PathVariable Long id,
                                   @RequestParam LeadStatus newStatus,
                                   @RequestParam String updatedBy,
                                   @RequestParam(required = false) String note,
                                   @RequestParam(required = false) LocalDateTime followUpDate,
                                   @RequestParam(required = false) LocalDateTime siteVisitDate) {
        return leadService.updateLeadStatus(id, newStatus, updatedBy, note, followUpDate, siteVisitDate);
    }

    @GetMapping("/{id}/audit-log")
    public List<LeadAuditLog> getAuditLog(@PathVariable Long id) {
        return leadService.getAuditLogs(id);
    }
    
 // New: Create a lead audit log
    @PostMapping("/{id}/audit-log")
    public LeadAuditLog createAuditLog(@PathVariable Long id, @RequestBody LeadAuditLog log) {
        return leadService.createAuditLog(id, log);
    }

    // New: Update a lead audit log
    @PutMapping("/audit-log/{logId}")
    public LeadAuditLog updateAuditLog(@PathVariable Long logId, @RequestBody LeadAuditLog log) {
        return leadService.updateAuditLog(logId, log);
    }

    // New: Delete a lead audit log
    @DeleteMapping("/audit-log/{logId}")
    public void deleteAuditLog(@PathVariable Long logId) {
        leadService.deleteAuditLog(logId);
    }

    @GetMapping
    public List<Leads> getAllLeads() {
        return leadService.getAllLeads();
    }
    
    
    @GetMapping("/summary")
    public LeadSummaryDTO getLeadSummary() {
        return leadService.getLeadSummary();
    }

}
