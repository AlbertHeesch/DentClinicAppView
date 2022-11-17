package com.dentclinic.view.dentclinic_view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DentClinicViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentClinicViewApplication.class, args);
    }

}
