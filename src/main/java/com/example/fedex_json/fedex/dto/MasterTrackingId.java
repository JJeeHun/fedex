package com.example.fedex_json.fedex.dto;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <h2>RequestedShipment Information </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">formId</b> : 8600<br/>
 *   <li><b style="color: #86da98;">trackingIdType</b> : EXPRESS<br/>
 *   <li><b style="color: #86da98;">uspsApplicationId</b> : 92<br/>
 *   <li><b style="color: #86da98;">trackingNumber</b> : 49XXX0000XXX20032835<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class MasterTrackingId {
    private String formId;
    private String trackingIdType;
    private String uspsApplicationId;
    private String trackingNumber;
}
