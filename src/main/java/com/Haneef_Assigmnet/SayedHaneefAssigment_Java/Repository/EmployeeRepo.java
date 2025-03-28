package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Repository;

import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO.EmployeePageResponse;
import com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.Employee;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepo extends CouchDbRepositorySupport<Employee> {

    @Autowired
    public EmployeeRepo(CouchDbConnector db) {
        super(Employee.class, db);
        initStandardDesignDocument();
    }


//    public void addNewEmployee(Employee employee) {
//        add(employee); // Save to CouchDB
//
//        String managerEmail = getManagerEmail(employee); // Implement logic to get Level 1 Manager's email
//        if (managerEmail != null) {
//            emailService.sendManagerNotification(managerEmail, employee.getEmployeeName(), employee.getPhoneNumber(), employee.getEmail());
//        }
//    }

    public Employee findByEmail(String email) {
        List<Employee> employees = getAll();
        return employees.stream()
                .filter(emp -> emp.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Employee findEmployeeById(String id) {
        try {
            return get(id);       //This will return employee object
        } catch (Exception e) {
            return null;
        }
    }



    public EmployeePageResponse getPaginatedEmployees(int page, int size, String sortBy, String sortDirection) {

        List<Employee> allEmployees = getAll();


        List<Employee> sortedEmployees =
                sortEmployees(allEmployees, sortBy, sortDirection);

        // Paginating
        List<Employee> paginatedEmployees = paginateEmployees(sortedEmployees, page, size);

        // Calculating pagination metadata
        int totalElements = allEmployees.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean isLastPage = page >= totalPages - 1;

        return new EmployeePageResponse(
                paginatedEmployees,
                page,
                size,
                totalElements,
                totalPages,
                isLastPage
        );
    }

    private List<Employee> sortEmployees(List<Employee> employees, String sortBy, String sortDirection) {
        Comparator<Employee> comparator = switch (sortBy.toLowerCase()) {
            case "email" -> Comparator.comparing(Employee::getEmail);
            case "name","employeename" -> Comparator.comparing(Employee::getEmployeeName);
            default -> Comparator.comparing(Employee::getId);
        };

        // Appling sort direction
        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }

        return employees.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Employee> paginateEmployees(List<Employee> sortedEmployees, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, sortedEmployees.size());

        if (start > sortedEmployees.size()) {
            return new ArrayList<>();
        }

        return sortedEmployees.subList(start, end);
    }
}
