package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.PackagingType;
import com.example.fedex_json.fedex.enums.RateRequestType;
import com.example.fedex_json.fedex.enums.ServiceType;
import com.example.fedex_json.fedex.enums.PickupType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <h2>RequestedShipment Information </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">shipDatestamp</b> [배송일] : 2019-10-14 [default 현재일자]<br/>
 *   <li><b style="color: #86da98;">totalDeclaredValue</b> : {@link TotalDeclaredValue}<br/>
 *   <li><b style="color: #ee6196;">shipper</b> : {@link Shipper}<br/>
 *   <li><b style="color: #86da98;">soldTo</b> : {@link SoldTo}<br/>
 *   <li><b style="color: #ee6196;">recipients</b> : List<{@link Recipient}><br/>
 *   <li><b style="color: #86da98;">recipientLocationNumber</b> : 1234567<br/>
 *   <li><b style="color: #ee6196;">pickupType</b> : {@link PickupType}<br/>
 *   <li><b style="color: #ee6196;">serviceType</b> : {@link ServiceType}<br/>
 *   <li><b style="color: #ee6196;">packagingType</b> : {@link PackagingType}<br/>
 *   <li><b style="color: #ee6196;">totalWeight</b> : 10.6 [List<{@link RequestedPackageLineItem}> 총 중량]<br/>
 *   <li><b style="color: #86da98;">origin</b> : {@link Origin}<br/>
 *   <li><b style="color: #ee6196;">shippingChargesPayment</b> : {@link ShippingChargesPayment}<br/>
 *   <li><b style="color: #86da98;">shipmentSpecialServices</b> : {@link ShipmentSpecialServices}<br/>
 *   <li><b style="color: #86da98;">emailNotificationDetail</b> : {@link EmailNotificationDetail}<br/>
 *   <li><b style="color: #86da98;">expressFreightDetail</b> : {@link ExpressFreightDetail}<br/>
 *   <li><b style="color: #86da98;">variableHandlingChargeDetail</b> : {@link VariableHandlingChargeDetail}<br/>
 *   <li><b style="color: #86da98;">customsClearanceDetail</b> : {@link CustomsClearanceDetail}<br/>
 *   <li><b style="color: #86da98;">smartPostInfoDetail</b> : {@link SmartPostInfoDetail}<br/>
 *   <li><b style="color: #86da98;">blockInsightVisibility</b> : {@link BlockInsightVisibility}<br/>
 *   <li><b style="color: #ee6196;">labelSpecification</b> : {@link LabelSpecification}<br/>
 *   <li><b style="color: #86da98;">shippingDocumentSpecification</b> : {@link ShippingDocumentSpecification}<br/>
 *   <li><b style="color: #86da98;">rateRequestType</b> : {@link RateRequestType}<br/>
 *   <li><b style="color: #86da98;">preferredCurrency</b> : {@link PreferredCurrency}<br/>
 *   <li><b style="color: #86da98;">totalPackageCount</b> : {@link TotalPackageCount}<br/>
 *   <li><b style="color: #86da98;">masterTrackingId</b> : {@link MasterTrackingId}<br/>
 *   <li><b style="color: #ee6196;">requestedPackageLineItems</b> : List<{@link RequestedPackageLineItem}><br/>
 * </ul>
 *
 * <div>URL : <a style="color:#4883f4;" href="https://developer.fedex.com/api/ko-kr/catalog/ship/v1/docs.html">Fedex Api</a></div>
 */
@Builder @Jacksonized
@Getter @ToString
public class RequestedShipment {

    private String shipDatestamp;

    private TotalDeclaredValue totalDeclaredValue;

    @NotNull
    private Shipper shipper;

    private SoldTo soldTo;

    @NotNull
    private List<Recipient> recipients;

    private String recipientLocationNumber;

    @NotNull
    private PickupType pickupType;

    @NotNull
    private ServiceType serviceType;

    @NotNull
    private PackagingType packagingType;

    @NotNull
    private Double totalWeight;

    private Origin origin;

    @NotNull
    private ShippingChargesPayment shippingChargesPayment;

//    private ShipmentSpecialServices shipmentSpecialServices; -- Pass
//    private EmailNotificationDetail emailNotificationDetail; -- Pass
//    private ExpressFreightDetail expressFreightDetail; -- Pass
//    private VariableHandlingChargeDetail variableHandlingChargeDetail; -- Pass
//    private CustomsClearanceDetail customsClearanceDetail; -- Pass
//    private SmartPostInfoDetail smartPostInfoDetail; -- Pass
    private boolean blockInsightVisibility; // 배송인/지불인만 볼 수 있다 - default : false
    private LabelSpecification labelSpecification;
//    private ShippingDocumentSpecification shippingDocumentSpecification; -- Pass
    private List<RateRequestType> rateRequestType;
    private String preferredCurrency;
    private Integer totalPackageCount;

    private MasterTrackingId masterTrackingId;

    @NotNull
    private List<RequestedPackageLineItem> requestedPackageLineItems;
}
