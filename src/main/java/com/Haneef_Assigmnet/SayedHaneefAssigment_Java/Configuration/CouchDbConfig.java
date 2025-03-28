package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Configuration;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CouchDbConfig {

    @Bean
    public CouchDbConnector couchDbConnector() throws Exception {
        HttpClient httpClient = new StdHttpClient.Builder()
                .host("localhost")
                .username("Haneef")
                .password("haneef")
                .port(5984)
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("employee", dbInstance);
        db.createDatabaseIfNotExists();
        return db;
    }
}
