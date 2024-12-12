package com.example.fedex_json.fedex.dto;

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
 *   <li><b style="color: #86da98;">masterTrackingNumber</b> : 794XXXXX5000<br/></li>
 *   <li><b style="color: #86da98;">deliveryDatestamp</b> : 2012-09-23<br/></li>
 *   <li><b style="color: #86da98;">trackingNumber</b> : 49XXX0000XXX20032835<br/></li>
 *   <li><b style="color: #86da98;">currency</b> : USD<br/></li>
 *   <li><b style="color: #86da98;">additionalChargesDiscount</b> : 621.45<br/></li>
 *   <li><b style="color: #86da98;">netRateAmount</b> : 1.45<br/></li>
 *   <li><b style="color: #86da98;">netChargeAmount</b> : 21.45<br/></li>
 *   <li><b style="color: #86da98;">netDiscountAmount</b> : 121.45<br/></li>
 *   <li><b style="color: #86da98;">codcollectionAmount</b> : 231.45<br/></li>
 *   <li><b style="color: #86da98;">baseRateAmount</b> : 321.45<br/></li>
 *   <li><b style="color: #86da98;">packageDocuments</b> : List<{@link PackageDocument}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class PieceResponse {

    private String masterTrackingNumber;
    private String deliveryDatestamp;
    private String trackingNumber;
    private String currency;
    private Double additionalChargesDiscount;
    private Double netRateAmount;
    private Double netChargeAmount;
    private Double netDiscountAmount;
    private Double codcollectionAmount;
    private Double baseRateAmount;
    private List<PackageDocument> packageDocuments;
}
