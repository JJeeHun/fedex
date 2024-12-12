package com.example.fedex_json.fedex.service;

import com.example.fedex_json.fedex.auth.OAuthRequest;
import com.example.fedex_json.fedex.dto.*;
import com.example.fedex_json.fedex.dto.Error;
import com.example.fedex_json.fedex.enums.PaymentType;
import com.example.fedex_json.fedex.shipment.CreateShipmentBody;
import com.example.fedex_json.fedex.shipment.ShipmentCancelResponse;
import com.example.fedex_json.fedex.shipment.ShipmentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Slf4j
public class ShipmentService {

    private OAuthRequest authRequest;
    private final static String PROD_URL = "https://apis.fedex.com/ship/v1/shipments";
    private final static String TEST_URL = "https://apis-sandbox.fedex.com/ship/v1/shipments";
    private final String URL;
    private final RestTemplate API;
    private final ObjectMapper om = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public ShipmentService(OAuthRequest authRequest) {
        this.authRequest = authRequest;
        this.API = getRestTemplate();

        if(this.authRequest.isProd()) this.URL = PROD_URL;
        else this.URL = TEST_URL;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add((request, body, execution) -> {
                    log.info("Fedex 요청 [ 요청 URI = '{}' ]", request.getURI());
                    log.info("Fedex 요청 [ 요청 DATA = {} ]", new String(body));
                    return execution.execute(request, body);
                });
        return restTemplate;
    }

    private void createShipmentValidation(CreateShipmentBody createShipmentBody) {
        // root
        Assert.notNull(createShipmentBody.getRequestedShipment(),"RequestedShipment is Required");
        Assert.notNull(createShipmentBody.getLabelResponseOptions(),"LabelResponseOptions is Required");
        Assert.notNull(createShipmentBody.getAccountNumber(),"AccountNumber is Required");

        // 1 level
        RequestedShipment requestedShipment = createShipmentBody.getRequestedShipment();
        Assert.notNull(requestedShipment.getShipper(), "RequestedShipment -> Shipper is Required");
        Assert.notEmpty(requestedShipment.getRecipients(),"RequestedShipment -> Recipients is Required");
        Assert.notNull(requestedShipment.getPickupType(),"RequestedShipment -> PickupType is Required");
        Assert.notNull(requestedShipment.getServiceType(),"RequestedShipment -> ServiceType is Required");
        Assert.notNull(requestedShipment.getPackagingType(),"RequestedShipment -> PackagingType is Required");
        Assert.notNull(requestedShipment.getTotalWeight(),"RequestedShipment -> TotalWeight is Required");
        Assert.isTrue(requestedShipment.getTotalWeight() > 0,"RequestedShipment -> TotalWeight must be greater than 0");
        Assert.notNull(requestedShipment.getShippingChargesPayment(),"RequestedShipment -> ShippingChargesPayment is Required");
        Assert.notNull(requestedShipment.getLabelSpecification(),"RequestedShipment -> LabelSpecification is Required");
        Assert.notEmpty(requestedShipment.getRequestedPackageLineItems(),"RequestedShipment -> RequestedPackageLineItems is Required");

        AccountNumber accountNumber = createShipmentBody.getAccountNumber();
        Assert.hasText(accountNumber.getValue(),"RequestedShipment -> AccountNumber Value is Required");

        // 2 Level
        // Shipper
        Shipper shipper = requestedShipment.getShipper();
        Assert.notNull(shipper.getAddress(),"RequestedShipment -> Shipper -> Address is Required");
        Assert.notNull(shipper.getContact(),"RequestedShipment -> Shipper -> Contact is Required");

        // Recipient
        List<Recipient> recipients = requestedShipment.getRecipients();
        for (Recipient recipient : recipients) {
            Assert.notNull(recipient.getAddress(),"RequestedShipment -> Recipient -> Address is Required");
            Assert.notNull(recipient.getContact(),"RequestedShipment -> Recipient -> Contact is Required");
        }

        // ShippingChargesPayment
        ShippingChargesPayment shippingChargesPayment = requestedShipment.getShippingChargesPayment();
        Assert.notNull(shippingChargesPayment.getPaymentType(),"RequestedShipment -> ShippingChargesPayment -> PaymentType is Required");

        if( shippingChargesPayment.getPaymentType() != PaymentType.SENDER ) {
            Assert.notNull(shippingChargesPayment.getPayor(),"RequestedShipment -> ShippingChargesPayment -> Payor is Required");
            Assert.notNull(shippingChargesPayment.getPayor().getResponsibleParty(),
                "RequestedShipment -> ShippingChargesPayment -> Payor -> ResponsibleParty is Required");
            Assert.notNull(shippingChargesPayment.getPayor().getResponsibleParty().getAccountNumber(),
                "RequestedShipment -> ShippingChargesPayment -> Payor -> ResponsibleParty -> AccountNumber is Required");
            Assert.hasText(shippingChargesPayment.getPayor().getResponsibleParty().getAccountNumber().getValue(),
                "RequestedShipment -> ShippingChargesPayment -> Payor -> ResponsibleParty -> AccountNumber -> Value is Required");
        }

        // LabelSpecification
        LabelSpecification labelSpecification = requestedShipment.getLabelSpecification();
        Assert.notNull(labelSpecification.getLabelFormatType(),"RequestedShipment -> LabelSpecification -> LabelFormatType is Required");
        Assert.notNull(labelSpecification.getImageType(),"RequestedShipment -> LabelSpecification -> ImageType is Required");
        Assert.notNull(labelSpecification.getLabelStockType(),"RequestedShipment -> LabelSpecification -> LabelStockType is Required");

        // RequestedPackageLineItem
        List<RequestedPackageLineItem> requestedPackageLineItems = requestedShipment.getRequestedPackageLineItems();
        for ( RequestedPackageLineItem packageLineItem: requestedPackageLineItems ) {
            Assert.notNull(packageLineItem.getWeight(),"RequestedShipment -> RequestedPackageLineItem -> Weight is Required");
            Assert.notNull(packageLineItem.getWeight().getUnits(),"RequestedShipment -> RequestedPackageLineItem -> Weight -> Units is Required");
            Assert.notNull(packageLineItem.getWeight().getValue(),"RequestedShipment -> RequestedPackageLineItem -> Weight -> Value is Required");
            Assert.isTrue(packageLineItem.getWeight().getValue() > 0,"RequestedShipment -> RequestedPackageLineItem -> Weight -> Value must be greater than 0");
        }

    }

    public ShipmentResponse createShipment(String customerTransactionId, CreateShipmentBody createShipmentBody) {
        createShipmentValidation(createShipmentBody);

        log.info("Fedex createShipment 요청");
        HttpHeaders httpHeaders = authRequest.getHeader();
        httpHeaders.add("x-customer-transaction-id" ,customerTransactionId);
        HttpEntity<CreateShipmentBody> requestEntity = new HttpEntity<>(createShipmentBody, httpHeaders);
        ShipmentResponse shipmentResponse = null;

        try {
            ResponseEntity<HashMap> responseEntity = API.postForEntity(URL, requestEntity, HashMap.class);
            log.info("Fedex 요청 성공");
            String jsonString = om.writeValueAsString(responseEntity.getBody());
            log.info("Fedex [개발 완료 후 삭제] JSON String => {}", jsonString);
            shipmentResponse = om.readValue(jsonString, ShipmentResponse.class);
        } catch (HttpClientErrorException e) {
            log.info("Fedex 요청 에러");
            shipmentResponse = gzipError(e.getResponseBodyAsByteArray(), ShipmentResponse.class);
            assert shipmentResponse != null;
            printErrors(shipmentResponse.getErrors());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Fedex createShipment 요청 완료");
        return shipmentResponse;
    }

    public ShipmentCancelResponse cancelShipment(String accountNumber, String trackingNumber) {
        log.info("Fedex cancelShipment 요청");
        Assert.hasText(accountNumber,"AccountNumber is Required");
        Assert.hasText(trackingNumber,"TrackingNumber is Required");

        HttpHeaders httpHeaders = authRequest.getHeader();
        Map<String, Object> body = new HashMap<>() {{
            put("accountNumber", AccountNumber.builder().value(accountNumber).build());
            put("trackingNumber",trackingNumber);
        }};
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
        ShipmentCancelResponse shipmentResponse = null;

        try {
            ResponseEntity<HashMap> responseEntity = getRestTemplate().exchange(URL + "/cancel", HttpMethod.PUT, requestEntity, HashMap.class);
            log.info("Fedex 요청 성공");
            String jsonString = om.writeValueAsString(responseEntity.getBody());
            log.info("Fedex [개발 완료 후 삭제] JSON String => {}", jsonString);
            shipmentResponse = om.readValue(jsonString, ShipmentCancelResponse.class);
        } catch (HttpClientErrorException e) {
            log.info("Fedex 요청 에러");
            ShipmentCancelResponse shipmentCancelResponse = gzipError(e.getResponseBodyAsByteArray(), ShipmentCancelResponse.class);
            assert shipmentResponse != null;
            printErrors(shipmentResponse.getErrors());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Fedex cancelShipment 요청 완료");
        return shipmentResponse;
    }

    private <T> T gzipError(byte[] byteArray, Class<T> tClass) {
        try(
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ) {
            return om.readValue(gzipInputStream, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printErrors(List<Error> errors) {
        errors.forEach(error ->
            log.error("Fedex Error Code = '{}', ParameterList = '{}',Message = '{}'",
                error.getCode(),
                error.getParameterList(),
                error.getMessage())
        );
    }

}
