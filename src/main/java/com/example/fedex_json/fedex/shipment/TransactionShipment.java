package com.example.fedex_json.fedex.shipment;

import com.example.fedex_json.fedex.dto.PieceResponse;
import com.example.fedex_json.fedex.enums.ServiceType;
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
 *   <li><b style="color: #86da98;">serviceType</b> : {@link ServiceType}<br/></li>
 *   <li><b style="color: #86da98;">shipDatestamp</b> : 2019-10-14<br/></li>
 *   <li><b style="color: #86da98;">serviceCategory</b> : EXPRESS<br/></li>
 *   <li><b style="color: #86da98;">serviceName</b> : FedEx Ground<br/></li>
 *   <li><b style="color: #86da98;">completedShipmentDetail</b> : {@link CompletedShipmentDetail}<br/></li>
 *   <li><b style="color: #86da98;">pieceResponses</b> : List<{@link PieceResponse}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class TransactionShipment {
    private ServiceType serviceType;
    private String shipDatestamp;
    private String serviceCategory;
    private String serviceName;
    private CompletedShipmentDetail completedShipmentDetail;
    private List<PieceResponse> pieceResponses;
}
