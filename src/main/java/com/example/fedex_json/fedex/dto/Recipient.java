package com.example.fedex_json.fedex.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <h2>Recipient Information </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">address</b> : {@link Address}<br/>
 *   <li><b style="color: #ee6196;">contact</b> : {@link Contact}<br/>
 *   <li><b style="color: #86da98;">tins</b> : List<{@link Tin}><br/>
 *   <li><b style="color: #86da98;">deliveryInstructions</b> : Delivery Instructions<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Recipient {

    @NotNull
    private Address address;

    @NotNull
    private Contact contact;

    private List<Tin> tins;

    private String deliveryInstructions;
}
