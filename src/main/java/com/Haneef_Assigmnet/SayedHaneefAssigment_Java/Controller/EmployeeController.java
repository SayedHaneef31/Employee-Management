package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Controller;


import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeeDTO;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeePageRequest;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeePageResponse;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.Employee;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Service.EmployeeService;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


//    @GetMapping
//    public ResponseEntity<List<Employee>> getAllEmployees()
//    {
//        return ResponseEntity.ok(employeeService.getAll());
//    }

    @PostMapping
    public ResponseEntity<String> addEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
        try {
            String employeeId = employeeService.addEmployee(employeeDTO);
            return ResponseEntity.ok(employeeId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable String id,
            @Validated @RequestBody EmployeeDTO employeeDTO
    ) {
        try {
            employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Intermediate Level Api's below

    @GetMapping("/{employeeId}/manager")
    public ResponseEntity<?> getNthLevelManager(
            @PathVariable String employeeId,
            @RequestParam(defaultValue = "1") int level
    ){
        try {
            // Validate input parameters
            if (level < 1) {
                return ResponseEntity.badRequest().body("Level must be 1 or greater");
            }

            Employee manager = employeeService.getNthLevelManager(employeeId, level);

            if (manager == null) {
                return ResponseEntity.ok("No manager found at this level");
            }

            // Creating a DTO to return only necessary information
            Map<String, String> managerInfo = new HashMap<>();
            managerInfo.put("id", manager.getId());
            managerInfo.put("name", manager.getEmployeeName());
            managerInfo.put("email", manager.getEmail());

            return ResponseEntity.ok(managerInfo);
        } catch (RuntimeException e) {

            System.err.println("Error retrieving manager: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<EmployeePageResponse> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "employeeName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        EmployeePageResponse response = employeeService.getAllEmployees(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }
}
