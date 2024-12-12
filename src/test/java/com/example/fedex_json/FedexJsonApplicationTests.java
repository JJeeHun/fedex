package com.example.fedex_json;

import com.example.fedex_json.fedex.FedexRepository;
import com.example.fedex_json.fedex.auth.OAuthRequest;
import com.example.fedex_json.fedex.dto.*;
import com.example.fedex_json.fedex.enums.*;
import com.example.fedex_json.fedex.service.ShipmentService;
import com.example.fedex_json.fedex.shipment.CreateShipmentBody;
import com.example.fedex_json.fedex.shipment.ShipmentCancelResponse;
import com.example.fedex_json.fedex.shipment.ShipmentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//@SpringBootTest
class FedexJsonApplicationTests {

    private final String mode = "TEST";
    private final OAuthRequest request = new OAuthRequest("l701a1989032b34ddea0b9fe3ec7829ef5", "3c3184c058a04ee69717bfc70d5e7a8d", mode);
    private final ShipmentService shipmentService = new ShipmentService( request );

    @Autowired
    private FedexRepository fedexRepository;

    private static final Shipper SHIPPER = Shipper.builder()
            .contact(Contact.builder() // TODO - 하드코딩
                    .personName("JASON LEE")
                    .companyName("FLEXFIT LLC.")
                    .phoneNumber("7144478052")
                    .build())
            .address(Address.builder() // TODO - 하드코딩
                    .streetLines(Arrays.asList("625 COLUMBIA ST"))
                    .city("BREA")
                    .stateOrProvinceCode(USStateCodeType.CA)
                    .postalCode("92821")
                    .countryCode(CountryCodeType.US)
                    .build())
            .build();

    private static final LabelSpecification LABEL_SPECIFICATION = LabelSpecification.builder()
            .labelFormatType(LabelFormatType.COMMON2D)
            .imageType(ImageType.PNG)
            .labelStockType(LabelStockType.PAPER_4X6)
            .build();

    private static final ShippingChargesPayment SHIPPING_CHARGES_PAYMENT = ShippingChargesPayment.builder()
            .paymentType(PaymentType.SENDER) // TODO - THIRD_PARTY 가 아닐 경우만
            .payor(Payor.builder()
                    .responsibleParty(ResponsibleParty.builder()
                            .accountNumber(AccountNumber.builder()
                                    .value("740561073") // TODO - THIRD_PARTY 일 경우 ThirdPartyAccount 의 키를 바인딩
                                    .build())
                            .build())
                    .build())
            .build();


    private RequestedPackageLineItem getRequestedPackageLineItem(int seq,
                                                                 double weight,
                                                                 int length,
                                                                 int width,
                                                                 int height,
                                                                 String customerRef,
                                                                 String invoice,
                                                                 String poNumber
    ) {
        return RequestedPackageLineItem.builder()
                .sequenceNumber(seq)
                .groupPackageCount(1)
                .weight(Weight.builder()
                        .units(UnitWeightType.LB)
                        .value(weight)
                        .build())
                .dimensions(Dimensions.builder()
                        .units(UnitLengthType.IN)
                        .length(length)
                        .width(width)
                        .height(height)
                        .build())
                .customerReferences(Arrays.asList(
                        CustomerReference.builder()
                                .customerReferenceType(CustomerReferenceType.CUSTOMER_REFERENCE)
                                .value(customerRef)
                                .build(),
                        CustomerReference.builder()
                                .customerReferenceType(CustomerReferenceType.INVOICE_NUMBER)
                                .value(invoice)
                                .build(),
                        CustomerReference.builder()
                                .customerReferenceType(CustomerReferenceType.P_O_NUMBER)
                                .value(poNumber)
                                .build()
                ))
                .build();
    }


    private final CreateShipmentBody createShipmentBody = CreateShipmentBody.builder()
            .labelResponseOptions(LabelResponseOptionType.URL_ONLY) /* 이미지 형식을 url 또는 label ( 인코딩값 )*/
            .accountNumber(AccountNumber.builder()
                .value("740561073")
                .build()
            )
            .requestedShipment(RequestedShipment.builder()
                    .serviceType(ServiceType.valueOf("FEDEX_GROUND")) // TODO - 하드코딩  - 서비스 타입 확인 후 어떻게 바인딩 할지 결정
                    .pickupType(PickupType.USE_SCHEDULED_PICKUP) // TODO - 필수 없는 듯
                    .totalWeight(10d)                            // TODO - 필수 없는 듯 - item의 weight으로
                    .packagingType(PackagingType.YOUR_PACKAGING)
                    .recipients(Arrays.asList(
                            Recipient.builder()
                                    .contact(Contact.builder()
                                            .personName("ASHLEY JORGENSEN")
                                            .companyName("DIAMOND L DESIGN")
                                            .phoneNumber("8017988591")
                                            .build())
                                    .address(Address.builder()
                                            .streetLines(Arrays.asList("1283 EXPRESSWAY LANE",""))
                                            .city("SPANISH FORK")
                                            .stateOrProvinceCode(USStateCodeType.valueOf("UT"))
                                            .postalCode("84660")
                                            .countryCode(CountryCodeType.US) // TODO - FEDEX 는 US만 처리
                                            .build())
                                    .build()
                    ))
                    .shipper(SHIPPER)
                    .shippingChargesPayment(SHIPPING_CHARGES_PAYMENT)
                    .labelSpecification(LABEL_SPECIFICATION)
                    .requestedPackageLineItems(Arrays.asList(getRequestedPackageLineItem(1,3,16,7,9,"Flexfit Merchandise","SO24006762123213","1983333")))
                    .build())
            .build();


    @Test
    @DisplayName("Fedex Create Ship")
    void createShipment() {
        // when
        ShipmentResponse shipment = shipmentService.createShipment("createShipment",createShipmentBody);

        // then
        Assertions.assertNotNull(shipment);
        Assertions.assertNotNull(shipment.getOutput());
        Assertions.assertNull(shipment.getErrors());
    }

    @Test
    @DisplayName("Fedex Ship 취소")
    void cancelShipment() {
        // given
        ShipmentResponse shipment = shipmentService.createShipment("cancelShipment",createShipmentBody);
        String trackingNumber = shipment.getOutput()
            .getTransactionShipments().get(0)
            .getCompletedShipmentDetail()
            .getMasterTrackingId()
            .getTrackingNumber();

        // when
        ShipmentCancelResponse shipmentCancelResponse = shipmentService.cancelShipment(createShipmentBody.getAccountNumber().getValue(), trackingNumber);

        // then
        Assertions.assertNotNull(shipmentCancelResponse);
        Assertions.assertNotNull(shipmentCancelResponse.getOutput());
        Assertions.assertNotNull(shipmentCancelResponse.getOutput().getAlerts());
        Assertions.assertNull(shipmentCancelResponse.getErrors());
    }

    @Test
    @DisplayName("Sender가 아닐 경우 검증 에러")
    void validationNotSenderShipment() {
        // given
        CreateShipmentBody createShipmentBody = CreateShipmentBody.builder()
            .labelResponseOptions(LabelResponseOptionType.LABEL) /* 이미지 형식을 url 또는 label ( 인코딩값 )*/
            .accountNumber(AccountNumber.builder()
                .value("740561073")
                .build()
            )
            .requestedShipment(RequestedShipment.builder()
                .serviceType(ServiceType.FEDEX_GROUND) // TODO - 하드코딩  - 서비스 타입 확인 후 어떻게 바인딩 할지 결정
                .pickupType(PickupType.USE_SCHEDULED_PICKUP) // TODO - 필수 없는 듯
                .totalWeight(10d)                            // TODO - 필수 없는 듯
                .packagingType(PackagingType.YOUR_PACKAGING)
                .recipients(Arrays.asList(
                    Recipient.builder()
                        .contact(Contact.builder()
                            .personName("ASHLEY JORGENSEN")
                            .companyName("DIAMOND L DESIGN")
                            .phoneNumber("8017988591")
                            .build())
                        .address(Address.builder()
                            .streetLines(Arrays.asList("1283 EXPRESSWAY LANE",""))
                            .city("SPANISH FORK")
                            .stateOrProvinceCode(USStateCodeType.valueOf("UT"))
                            .postalCode("84660")
                            .countryCode(CountryCodeType.US) // TODO - FEDEX 는 US만 처리
                            .build())
                        .build()
                ))
                .shipper(SHIPPER)
                .shippingChargesPayment(ShippingChargesPayment.builder()
                    .paymentType(PaymentType.THIRD_PARTY)
                    .payor(Payor.builder()
                        .responsibleParty(ResponsibleParty.builder()
                            .accountNumber(AccountNumber.builder()
                                .value("")
                                .build())
                            .build())
                        .build())
                    .build()
                )
                .labelSpecification(LABEL_SPECIFICATION)
                .requestedPackageLineItems(Arrays.asList(getRequestedPackageLineItem(1,3,16,7,9,"Flexfit Merchandise","SO24006762123213","1983333")))
                .build())
            .build();

        // when then
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> shipmentService.createShipment("validationNotSenderShipment", createShipmentBody),
            "RequestedShipment -> ShippingChargesPayment -> Payor -> ResponsibleParty -> AccountNumber -> Value is Required"
        );
    }

    @Test
    @DisplayName("Token 시간 만료전 동일 token")
    void noExpiredToken() {

        // when
        String token1 = request.getToken();
        String token2 = request.getToken();

        // then
        Assertions.assertEquals(token1,token2);
    }

    @Test
    @DisplayName("Token 시간 만료시 재요청")
    void expiredTokenRefresh(){

        // when
        String token1 = request.getToken();
        request.tokenClear();
        String token2 = request.getToken();

        // then
        Assertions.assertNotEquals(token1,token2);
    }

    @Test
    @DisplayName("계정정보가 다름")
    void accountError() {
        // when
        OAuthRequest oAuthRequest = new OAuthRequest("dkfjkdfj","fsdkfsjdf","test");

        // then
        Assertions.assertThrows(IllegalArgumentException.class, oAuthRequest::getToken, "OAuth 2.0 token retrieval failed");
    }

    @Test
    @DisplayName("주소 검증")
    void valiation() {
        String token1 = request.getToken();

        HttpHeaders header = request.getHeader();

        HashMap<Object, Object> body = new HashMap<>();
        HashMap<Object, Object> address = new HashMap<>();
        Address build = Address.builder()
            .streetLines(Arrays.asList("1283 EXPRESSWAY LANE1111", ""))
            .city("SPANISH FORK")
            .stateOrProvinceCode(USStateCodeType.valueOf("CA"))
            .postalCode("84660")
            .countryCode(CountryCodeType.US) // TODO - FEDEX 는 US만 처리
            .build();
        address.put("address", build);
        body.put("addressesToValidate", List.of(address));

        HttpEntity<HashMap> requestBody = new HttpEntity<>(body, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> hashMapResponseEntity = restTemplate.postForEntity("https://apis-sandbox.fedex.com/address/v1/addresses/resolve", requestBody, HashMap.class);

        System.out.println("hashMapResponseEntity = " + hashMapResponseEntity);

    }
}
