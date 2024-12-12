package com.example.fedex_json.fedex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98 ]
/**
 * <b style="font-size:15px;">SoldTo Information</b>
 * <br/>
 * <br/>
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">address</b> : {@link Address}</li>
 *   <li><b style="color: #86da98;">contact</b> : {@link Contact}</li>
 *   <li><b style="color: #86da98;">tins</b> : List<{@link Tin}></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class SoldTo {

    private Address address;
    private Contact contact;
    private List<Tin> tins;
    private AccountNumber accountNumber;
}
