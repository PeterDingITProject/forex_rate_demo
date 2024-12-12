package com.example.demo.service;

import com.example.demo.controller.ForexController;
import com.example.demo.vo.ForexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ForexControllerTest {

    @Autowired
    private ForexController forexController;

    @Test
    public void testGetForexRates_Success() {
        ForexRequest request = new ForexRequest();
        request.setStartDate("2024/12/01");
        request.setEndDate("2024/12/10");
        request.setCurrency("usd");

        ResponseEntity<?> response = forexController.getForexRates(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetForexRates_InvalidDateRange() {
        ForexRequest request = new ForexRequest();
        request.setStartDate("2022/01/01");
        request.setEndDate("2025/01/10");
        request.setCurrency("usd");

        ResponseEntity<?> response = forexController.getForexRates(request);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }
}
