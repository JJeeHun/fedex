package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <h2>ShippingChargesPayment Information </h2>
 *
 * <b>Color Information : </b>
 * <br />
 * <b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b> ,   <b style="color: #ffa500;">conditional</b>
 * <br />
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">paymentType</b> : {@link PaymentType}<br/>
 *   <li><b style="color: #ffa500;">payor</b> : {@link Payor} [PaymentType Not SENDER is Required]<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ShippingChargesPayment {

    @NotNull
    private PaymentType paymentType;

    private Payor payor;
}
