package com.example.fedex_json.fedex.shipment;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]

import com.example.fedex_json.fedex.dto.Alert;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">transactionShipments</b> : List<{@link TransactionShipment}><br/></li>
 *   <li><b style="color: #86da98;">alerts</b> : List<{@link Alert}><br/></li>
 *   <li><b style="color: #86da98;">jobId</b> : abc123456<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ShipmentOutPut {

    private List<TransactionShipment> transactionShipments;
    private List<Alert> alerts;
    private String jobId;
}
