package com.example.fedex_json.fedex.shipment;

import com.example.fedex_json.fedex.dto.Alert;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">cancelledShipment</b> : true<br/></li>
 *   <li><b style="color: #86da98;">cancelledHistory</b> : true<br/></li>
 *   <li><b style="color: #86da98;">successMessage</b> : Success<br/></li>
 *   <li><b style="color: #86da98;">alerts</b> : List<{@link Alert}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ShipmentCancelOutPut {
    private boolean cancelledShipment;
    private boolean cancelledHistory;
    private String successMessage;
    private List<Alert> alerts;
}
