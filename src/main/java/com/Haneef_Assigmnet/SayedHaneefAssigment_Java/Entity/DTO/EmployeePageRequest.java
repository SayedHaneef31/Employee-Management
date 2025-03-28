package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO;


//import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

//@Schema(description = "Pagination and Sorting Request for Employees")
public class EmployeePageRequest {
    //@Schema(description = "Page number (0-based)", defaultValue = "0")
    @Min(value = 0, message = "Page number must be non-negative")
    private int page = 0;

    //@Schema(description = "Number of items per page", defaultValue = "10")
    @Positive(message = "Page size must be positive")
    private int size = 10;

    //@Schema(description = "Sorting field", allowableValues = "name,email,id")
    private String sortBy = "employeeName";

    //@Schema(description = "Sorting direction", allowableValues = "asc,desc")
    private String sortDirection = "asc";


    public EmployeePageRequest() {}

    public EmployeePageRequest(int page, int size, String sortBy, String sortDirection) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}