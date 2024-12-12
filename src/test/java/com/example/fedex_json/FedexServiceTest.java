package com.example.fedex_json;

import com.example.fedex_json.fedex.FedexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FedexServiceTest {

    @Autowired
    private FedexService fedexService;

    @Test
    void createShipmentTest() {
        fedexService.fedexSearchAndCreateShipment("S2024102900057", "SO24017839", "21");
    }

}
