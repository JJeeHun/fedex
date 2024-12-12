package com.example.fedex_json.fedex.dto;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">address</b> : {@link Address}<br/>
 *   <li><b style="color: #86da98;">contact</b> : {@link Contact}<br/>
 *   <li><b style="color: #ee6196;">accountNumber</b> : {@link AccountNumber}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ResponsibleParty {

    private Address address;
    private Contact contact;

    @NotNull
    private AccountNumber accountNumber;
}
