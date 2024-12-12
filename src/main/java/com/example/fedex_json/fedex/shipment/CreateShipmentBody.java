package com.example.fedex_json.fedex.shipment;

import com.example.fedex_json.fedex.dto.AccountNumber;
import com.example.fedex_json.fedex.dto.RequestedShipment;
import com.example.fedex_json.fedex.enums.LabelResponseOptionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">requestedShipment</b> : {@link RequestedShipment}<br/></li>
 *   <li><b style="color: #ee6196;">labelResponseOptions</b> : {@link LabelResponseOptionType}<br/></li>
 *   <li><b style="color: #ee6196;">accountNumber</b> : {@link AccountNumber}<br/></li>
 *   <li><b style="color: #ee6196;">oneLabelAtATime</b> : true = 1 to N<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CreateShipmentBody {
    @NotNull
    private RequestedShipment requestedShipment;

    @NotNull
    private LabelResponseOptionType labelResponseOptions;

    @NotNull
    private AccountNumber accountNumber;

    private boolean oneLabelAtATime;

}
