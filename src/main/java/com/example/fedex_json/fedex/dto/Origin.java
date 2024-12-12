package com.example.fedex_json.fedex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <h2>Origin [발송인 주소와 다를경우 발송지] </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">contact</b> : {@link Contact}<br/>
 *   <li><b style="color: #86da98;">address</b> : {@link Address}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Origin {

    private Contact contact;
    private Address address;
}
