package com.example.fedex_json.fedex;

import com.example.fedex_json.fedex.auth.OAuthRequest;
import com.example.fedex_json.fedex.dto.*;
import com.example.fedex_json.fedex.enums.*;
import com.example.fedex_json.fedex.service.ShipmentService;
import com.example.fedex_json.fedex.shipment.CreateShipmentBody;
import com.example.fedex_json.fedex.shipment.ShipmentCancelResponse;
import com.example.fedex_json.fedex.shipment.ShipmentResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FedexService {

    private final FedexRepository fedexRepository;
    private final String mode = "TEST";
    private final ShipmentService shipmentService = new ShipmentService(
        new OAuthRequest("l701a1989032b34ddea0b9fe3ec7829ef5","3c3184c058a04ee69717bfc70d5e7a8d",mode)
    );
    private static final LabelSpecification LABEL_SPECIFICATION = getLabelSpec();
    private static Shipper SHIPPER;
    private static ShippingChargesPayment DEFAULT_SHIPPING_CHARGES_PAYMENT;

    {
        initialize();
    }

    /**
     * Shipper 및 고정 Account를 디비로 가져올 경우 여기서 셋팅
     */
    @PostConstruct
    private void initialize() {
        SHIPPER = getSHIPPER("JASON LEE", "FLEXFIT LLC.", "7144478052",
            Arrays.asList("285 S BERRY ST"), "BREA", "92821");
        DEFAULT_SHIPPING_CHARGES_PAYMENT = getShippingChargePayment(PaymentType.SENDER, "740561073");
    }

    /**
     * 내부 데이터 호출 후 Fedex 호출
     * @param whOrderNo
     * @param orderNo
     * @param jobG
     * @return
     */
    public Map<String,Object> fedexSearchAndCreateShipment(String whOrderNo, String orderNo, String jobG) {
        List<Map<String, Object>> shippings = fedexRepository.findShipping(whOrderNo, orderNo, jobG);
        List<CreateShipDto> createShipDtos = new ArrayList<>();

        for (Map<String,Object> shipping: shippings) {
            Assert.isTrue("FEDEX".equals(shipping.get("ShipAgentCD")) ,"Fedex 요청 정보가 아닙니다.");

            double packageWeight = Double.parseDouble((String) shipping.get("PackageWeight"));

            // DB -> FEDEX Data Convert
            CreateShipDto createShipDto = CreateShipDto.builder()
                .serviceType(ServiceType.valueOf((String) shipping.get("ShipServiceCD")))
                .thirdPartyAccount((String) shipping.get("ThirdPartyAccount"))
//                .rootAccount("391887829")
                .rootAccount(DEFAULT_SHIPPING_CHARGES_PAYMENT.getPayor().getResponsibleParty().getAccountNumber().getValue())
                .totalWeight(packageWeight)
                .recipientPerson((String) shipping.get("ShipToPersonName"))
                .recipientCompany((String) shipping.get("ShipToCompanyName"))
                .recipientPhone((String) shipping.get("ShipToPhoneNumber"))
                .recipientStreetLines(Arrays.asList(
                    (String) shipping.get("ShipToStreetLines1"),
                    (String) shipping.get("ShipToStreetLines2"))
                )
                .recipientCity((String) shipping.get("ShipToCity"))
                .recipientStateOrProvinceCode(USStateCodeType.valueOf((String) shipping.get("ShipToStateProvinceCode")))
                .recipientPostalCode((String) shipping.get("ShipToPostalCode"))
                .seq(((Long) shipping.get("SEQ")).intValue())
                .packageCount(Integer.parseInt((String) shipping.get("PackageCount")))
                .packageWeight(packageWeight)
                .length(Integer.parseInt((String) shipping.get("DIMLength")))
                .width(Integer.parseInt((String) shipping.get("DIMWidth")))
                .height(Integer.parseInt((String) shipping.get("DIMHeight")))
                .invoice((String) shipping.get("CustomerInvoiceValue"))
                .po((String) shipping.get("CustomerPurchaseValue"))
                .isThirdParty("3RD PARTY".equals(shipping.get("ShipMthdCD")))
                .build();

            createShipDtos.add(createShipDto);
        }

        Map<String, Object> result = fedexCreateShipment(createShipDtos);

        return result;
    }

    /**
     * Fedex 요청 ( 내부 스펙 CreateShipDto 를 제외한 파라미터는 하드코딩 )
     * @param shippings
     * @return
     */
    public Map<String,Object> fedexCreateShipment(List<CreateShipDto> shippings) {
        SimpleDateFormat dateFormat  = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
        List<ShipmentResponse> results = new ArrayList<>();
        MasterTrackingId masterTrackingId = null;
        String customerTransactionId = dateFormat.format(new Date());

        for(CreateShipDto shipping : shippings) {
            CreateShipmentBody createShipmentBody = getShipmentBody(shipping, masterTrackingId);
            ShipmentResponse shipmentResponse = shipmentService.createShipment(customerTransactionId, createShipmentBody);

            results.add(shipmentResponse);

            if(shipmentResponse.getErrors() == null || shipmentResponse.getErrors().isEmpty()) {
                if (masterTrackingId == null) {
                    masterTrackingId = shipmentResponse.getOutput().getTransactionShipments().get(0)
                        .getCompletedShipmentDetail()
                        .getMasterTrackingId();
                }
            }else {
                break;
            }
        };

        return new HashMap<>(){{
            put("requestInfo", shippings);
            put("responseInfo", results);
        }};
    }

    public ShipmentCancelResponse cancelShipment(String accountNumber, String trackingNumber) {
        return shipmentService.cancelShipment(accountNumber, trackingNumber);
    }

    /**
     * Fedex CreateShipment Body 생성
     * @param shipping
     * @param masterTrackingId
     * @return
     */
    private CreateShipmentBody getShipmentBody(CreateShipDto shipping, MasterTrackingId masterTrackingId) {
        ShippingChargesPayment shippingChargesPayment = shipping.isThirdParty()
            ? getShippingChargePayment(PaymentType.THIRD_PARTY, shipping.getThirdPartyAccount()) : DEFAULT_SHIPPING_CHARGES_PAYMENT;

        return CreateShipmentBody.builder()
            .accountNumber(DEFAULT_SHIPPING_CHARGES_PAYMENT.getPayor().getResponsibleParty().getAccountNumber())
            .labelResponseOptions(LabelResponseOptionType.URL_ONLY)
            .oneLabelAtATime(true)
            .requestedShipment(RequestedShipment.builder()
                .shipDatestamp(LocalDate.now().toString())
                .totalPackageCount(shipping.getPackageCount())
                .serviceType(shipping.getServiceType())
                .packagingType(PackagingType.YOUR_PACKAGING)
                .pickupType(PickupType.USE_SCHEDULED_PICKUP)    // TODO - 필수 [기존에 없는 듯 ]
                .totalWeight(shipping.getTotalWeight())         // TODO - 필수 없는 듯
                .shipper(SHIPPER)
                .shippingChargesPayment(shippingChargesPayment) // TODO - 서드파티일 경우 객체 새로 생성
                .labelSpecification(LABEL_SPECIFICATION)
                .recipients(Arrays.asList(
                    Recipient.builder()
                        .contact(Contact.builder()
                            .personName(shipping.getRecipientPerson())
                            .companyName(shipping.getRecipientCompany())
                            .phoneNumber(shipping.getRecipientPhone()) // max 넘어감
                            .build())
                        .address(Address.builder()
                            .streetLines(shipping.getRecipientStreetLines())
                            .city(shipping.getRecipientCity())
                            .stateOrProvinceCode(shipping.getRecipientStateOrProvinceCode())
                            .postalCode(shipping.getRecipientPostalCode())
                            .countryCode(CountryCodeType.US)
                            .residential(ServiceType.GROUND_HOME_DELIVERY.equals(shipping.getServiceType()))
                            .build())
                        .build()
                ))
                .requestedPackageLineItems(Arrays.asList(
                    getRequestedPackageLineItem(shipping.getSeq(),
                        1,
                        shipping.getPackageWeight(),
                        shipping.getLength(),
                        shipping.getWidth(),
                        shipping.getHeight(),
                        shipping.getInvoice(),
                        shipping.getPo()
                    )
                ))
                .masterTrackingId(masterTrackingId) // TODO - 여러건일 경우 부모의 응답 값인 MasterTrackingId를 setting
                .build())
            .build();
    }

    private RequestedPackageLineItem getRequestedPackageLineItem(int seq,
                                                                 int packageCount,
                                                                 double weight,
                                                                 int length,
                                                                 int width,
                                                                 int height,
                                                                 String invoice,
                                                                 String poNumber
    ) {
        return RequestedPackageLineItem.builder()
                .sequenceNumber(seq)
//                .groupPackageCount(packageCount)
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
                        .value("Flexfit Merchandise")
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

    private static LabelSpecification getLabelSpec() {
        return LabelSpecification.builder()
                .labelFormatType(LabelFormatType.COMMON2D)
                .imageType(ImageType.PNG)
                .labelStockType(LabelStockType.PAPER_4X6)
                .build();
    }

    private ShippingChargesPayment getShippingChargePayment(PaymentType paymentType, String accountNumber) {
        return ShippingChargesPayment.builder()
                .paymentType(paymentType)
                .payor(Payor.builder()
                    .responsibleParty(ResponsibleParty.builder()
                        .accountNumber(AccountNumber.builder()
                            .value(accountNumber)
                            .build())
                        .build())
                    .build())
                .build();
    }

    private Shipper getSHIPPER(
        String person, String company, String phone,
        List<String> streetLines, String city, String postalCode
    ) {
        return Shipper.builder()
                .contact(Contact.builder() // TODO - 하드코딩
                    .personName(person)
                    .companyName(company)
                    .phoneNumber(phone)
                    .build())
                .address(Address.builder() // TODO - 하드코딩
                    .streetLines(streetLines)
                    .city(city)
                    .stateOrProvinceCode(USStateCodeType.CA)
                    .postalCode(postalCode)
                    .countryCode(CountryCodeType.US)
                    .build())
                .build();
    }
}
