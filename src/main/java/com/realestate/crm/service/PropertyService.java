package com.realestate.crm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.realestate.crm.model.Property;
import com.realestate.crm.repository.PropertyRepository;

@Service
public class PropertyService {
	
	private final PropertyRepository propertyRepository;
	
	
	public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

	public List<Property> getAllProperties(){
		return propertyRepository.findAll();
	}
	
	public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property updatedProperty) {
        return propertyRepository.findById(id)
                .map(property -> {
                    property.setName(updatedProperty.getName());
                    property.setDetails(updatedProperty.getDetails());
                    property.setLocation(updatedProperty.getLocation());
                    property.setPrice(updatedProperty.getPrice());
                    property.setType(updatedProperty.getType());
                    return propertyRepository.save(property);
                })
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
    public void savePropertiesFromExcel(MultipartFile file) throws IOException {
        List<Property> properties = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            //int lastRowNum = sheet.getLastRowNum();
            int lastRowNum = 3;
            for (int i = 1; i <= lastRowNum; i++) { // skip header
                Row row = sheet.getRow(i);

                if (row == null) continue;

                Property property = new Property();
                property.setName(getCellValue(row.getCell(0)));
                property.setDetails(getCellValue(row.getCell(1)));
                property.setLocation(getCellValue(row.getCell(2)));
                property.setPrice(Double.valueOf(getCellValue(row.getCell(3))));
                property.setType(getCellValue(row.getCell(4)));

                properties.add(property);
            }
        }

        propertyRepository.saveAll(properties);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }

}
