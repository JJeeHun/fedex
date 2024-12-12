package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.TinType;
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
 *   <li><b style="color: #86da98;">number</b> : 123567</li>
 *   <li><b style="color: #86da98;">tinType</b> : FEDERAL</li>
 *   <li><b style="color: #86da98;">usage</b> : usage</li>
 *   <li><b style="color: #ee6196;">effectiveDate</b> : 2024-06-13<br/>
 *   <li><b style="color: #86da98;">expirationDate</b> : 2024-06-13<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Tin {

    @NotNull
    @Size(max = 18)
    private String number;

    @NotNull
    private TinType tinType;
    private String usage;
    private String effectiveDate;
    private String expirationDate;
}
