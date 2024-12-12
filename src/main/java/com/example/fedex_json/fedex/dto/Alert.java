package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.AlertType;
import com.example.fedex_json.fedex.shipment.TransactionShipment;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">code</b> : SHIPMENT.VALIDATION.SUCCESS<br/></li>
 *   <li><b style="color: #86da98;">alertType</b> : {@link AlertType}<br/></li>
 *   <li><b style="color: #86da98;">message</b> : Shipment validated successfully. No errors found.<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Alert {
    private String code;
    private AlertType alertType;
    private String message;
}
