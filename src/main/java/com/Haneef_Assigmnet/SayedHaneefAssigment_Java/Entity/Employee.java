package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.ektorp.support.CouchDbDocument;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Employee extends CouchDbDocument {
    // CouchDbDocument already has an _id field
    // You can access/set it using getId() and setId() methods

    private String employeeName;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileImageUrl;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate()
//    {
//        createdAt= LocalDateTime.now();
//    }
}

