package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity.DTO;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class EmployeeDTO {
    @NotBlank(message = "Employee name is required")
    private String employeeName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String reportsTo;

    @NotBlank(message = "Profile image URL is required")
    private String profileImageUrl;
}