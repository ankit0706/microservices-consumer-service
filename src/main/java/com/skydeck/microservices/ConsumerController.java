package com.skydeck.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EurekaClient eurekaClient;

    @RequestMapping("/read")
    public String greeting() {
        InstanceInfo service = eurekaClient.getApplication("GATEWAY-SERVICE")
            .getInstances()
            .get(0);

        // System.out.println("service host: " + service.getHostName());
        // System.out.println("service port: " + service.getPort());
        // System.out.println("service url: " + service.getHomePageUrl());
        // String serviceIP = service.getIPAddr();
        // System.out.println("service url: " + service.getIPAddr());
        String hostName = "localhost";
        int port = service.getPort();
        String endpointName = "main";

        String url = "http://" + hostName + ":" + port + "/" + endpointName;
        System.out.println("URL to hit: " + url);
        // parameters can also be added
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }
}
