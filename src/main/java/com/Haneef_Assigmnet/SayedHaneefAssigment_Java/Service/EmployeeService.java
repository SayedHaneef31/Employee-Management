package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Service;

import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeeDTO;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeePageRequest;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeePageResponse;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.Employee;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Repository.EmployeeRepo;
//import org.ektorp.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<Employee> getAll()
    {
        List<Employee> list=employeeRepo.getAll();
        if(list.isEmpty()) throw new NullPointerException("No employee present in databse");
        return list;
    }



    public String addEmployee(EmployeeDTO employeeDTO) {

        if (employeeDTO.getReportsTo() != null) {
            validateReportingManager(employeeDTO.getReportsTo());
        }


        if (checkDuplicateEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }


        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setReportsTo(employeeDTO.getReportsTo());
        employee.setProfileImageUrl(employeeDTO.getProfileImageUrl());
        //employee.setCreatedAt(LocalDateTime.now());


        employeeRepo.add(employee);

        return employee.getId();
    }
    private void validateReportingManager(String managerId) {
        try {

            Employee manager = employeeRepo.get(managerId);
            if (manager == null) {
                throw new RuntimeException("Reporting manager not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid reporting manager ID");
        }
    }

    private boolean checkDuplicateEmail(String email) {

        return employeeRepo.findByEmail(email) != null;
    }

    public void deleteEmployee(String id) {
        Employee employee = employeeRepo.findEmployeeById(id);
        if (employee == null) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepo.remove(employee);
    }

    public void updateEmployee(String id, EmployeeDTO employeeDTO) {

        Employee existingEmployee = employeeRepo.findEmployeeById(id);
        if (existingEmployee == null) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }


        existingEmployee.setEmployeeName(employeeDTO.getEmployeeName());
        existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setReportsTo(employeeDTO.getReportsTo());
        existingEmployee.setProfileImageUrl(employeeDTO.getProfileImageUrl());


        employeeRepo.update(existingEmployee);
    }

    public Employee getNthLevelManager(String employeeId, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level must be 1 or greater");
        }
        Employee employee = employeeRepo.findEmployeeById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        return findNthLevelManager(employee, level);
    }


    private Employee findNthLevelManager(Employee employee, int level) {
        // Employee ka koi manager nahi ya hm pahuch gae final manager tak
        if (employee.getReportsTo() == null) {
            if (level == 1) {
                return null; // No manager found
            }
            throw new RuntimeException("Cannot find " + level + "th level manager");
        }

        Employee manager = employeeRepo.findEmployeeById(employee.getReportsTo());
        if (manager == null) {
            throw new RuntimeException("Manager not found");
        }


        if (level == 1) {
            return manager;
        }


        return findNthLevelManager(manager, level - 1);
    }

    public EmployeePageResponse getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "employeeName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return employeeRepo.getPaginatedEmployees(page, size, sortBy, sortDirection);
    }

    private List<EmployeeDTO> sortEmployees(List<EmployeeDTO> allEmployees, EmployeePageRequest pageRequest) {
        Comparator<EmployeeDTO> comparator = switch (pageRequest.getSortBy().toLowerCase()) {
            case "email" -> Comparator.comparing(EmployeeDTO::getEmail);
            case "reportsTo" -> Comparator.comparing(EmployeeDTO::getReportsTo, Comparator.nullsLast(String::compareTo));
            default -> Comparator.comparing(EmployeeDTO::getEmployeeName);
        };


        if ("desc".equalsIgnoreCase(pageRequest.getSortDirection())) {
            comparator = comparator.reversed();
        }

        return allEmployees.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Employee> paginateEmployees(List<Employee> sortedEmployees, EmployeePageRequest pageRequest) {
        int start = pageRequest.getPage() * pageRequest.getSize();
        int end = Math.min(start + pageRequest.getSize(), sortedEmployees.size());

        if (start > sortedEmployees.size()) {
            return new ArrayList<>();
        }

        return sortedEmployees.subList(start, end);
    }
}
