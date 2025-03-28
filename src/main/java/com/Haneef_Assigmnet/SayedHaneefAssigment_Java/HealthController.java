package com.Haneef_Assigmnet.SayedHaneefAssigment_Java;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController
{
    @GetMapping("/health")
    public String healthCheck()
    {
        return "Everything's working";
    }
}
