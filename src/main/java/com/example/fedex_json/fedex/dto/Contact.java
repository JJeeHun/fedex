package com.example.fedex_json.fedex.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98 ]
/**
 * <b style="font-size:15px;">Contact Information</b>
 * <br/>
 * <br/>
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">personName</b> : John Taylor</li>
 *   <li><b style="color: #86da98;">emailAddress</b> : sample@company.com</li>
 *   <li><b style="color: #86da98;">phoneExtension (내선번호)</b> : 1234</li>
 *   <li><b style="color: #ee6196;">phoneNumber</b> : 918xxxxx890<br/>
 *   <li><b style="color: #86da98;">companyName</b> : FedEx<br/>
 *   <li><b style="color: #86da98;">faxNumber</b> : 1234567890<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Contact {

    @Size(max = 70)
    private String personName;

    @Size(max = 80)
    private String emailAddress;

    @Size(max = 6)
    private String phoneExtension;

    /**
     * For US and CA, a phone number must have exactly 10 digits, plus an optional leading country code of '1' or '+1'
     * ( Max 15 )
     */
    @NotNull
    @Size(max = 10)
    private String phoneNumber;

    @Size(max = 35)
    private String companyName;

    private String faxNumber;
}
