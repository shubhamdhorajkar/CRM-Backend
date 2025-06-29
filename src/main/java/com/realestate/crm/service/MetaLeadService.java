package com.realestate.crm.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.realestate.crm.model.MetaLead;
import com.realestate.crm.repository.MetaLeadRepository;

@Service
public class MetaLeadService {

	@Autowired
    private MetaLeadRepository leadRepository;

    private final String ACCESS_TOKEN = "YOUR_PAGE_ACCESS_TOKEN";
    private final String FORM_ID = "YOUR_FORM_ID";
    private final String GRAPH_URL = "https://graph.facebook.com/v19.0/";

    public void fetchAndSaveLeads() {
        RestTemplate restTemplate = new RestTemplate();
        String url = GRAPH_URL + FORM_ID + "/leads?access_token=" + ACCESS_TOKEN;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

        if (data == null) return;

        for (Map<String, Object> leadMap : data) {
            String leadId = (String) leadMap.get("id");
            String createdTime = (String) leadMap.get("created_time");
            List<Map<String, Object>> fields = (List<Map<String, Object>>) leadMap.get("field_data");

            if (!leadRepository.existsByLeadId(leadId)) {
                MetaLead lead = new MetaLead();
                lead.setLeadId(leadId);
                lead.setSubmittedAt(LocalDateTime.parse(createdTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME));

                for (Map<String, Object> field : fields) {
                    String fieldName = (String) field.get("name");
                    List<String> values = (List<String>) field.get("values");

                    if ("full_name".equals(fieldName)) {
                        lead.setFullName(values.get(0));
                    } else if ("email".equals(fieldName)) {
                        lead.setEmail(values.get(0));
                    } else if ("phone_number".equals(fieldName)) {
                        lead.setPhone(values.get(0));
                    }
                    // Add more fields as needed
                }

                leadRepository.save(lead);
            }
        }
    }

    public List<MetaLead> getAllLeads() {
        return leadRepository.findAll();
    }
}
