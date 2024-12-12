package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForexRateServiceTest {

    @Autowired
    private ForexRateService forexRateService;

    @Test
    public void testFetchAndSaveForexRates() {
        forexRateService.fetchAndSaveForexRates();
        Mockito.verify(forexRateService, Mockito.times(1)).fetchAndSaveForexRates();
    }
}
