package com.example.fedex_json.fedex.dto;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]

import jakarta.validation.constraints.DecimalMax;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <h2>TotalDeclaredValue Information </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">amount</b> : 12.45<br/>
 *   <li><b style="color: #86da98;">currency</b> : USD<br/>
 *   <pre style="font-size:8px">      <i>Reference:</i> <a style="color: #4883f4;" href="https://developer.fedex.com/api/ko-kr/guides/api-reference.html#currencycodes">Postal Code Guide</a></li></pre>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class TotalDeclaredValue {
    @DecimalMax(value = "99999.99")
    private Double amount;

    private String currency;
}
