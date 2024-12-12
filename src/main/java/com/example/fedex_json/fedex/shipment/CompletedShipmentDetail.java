package com.example.fedex_json.fedex.shipment;

import com.example.fedex_json.fedex.dto.MasterTrackingId;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">masterTrackingId</b> : {@link MasterTrackingId}<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CompletedShipmentDetail {

    private MasterTrackingId masterTrackingId;
}
