package com.example.fedex_json.fedex.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <h2>AccountNumber Information </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">value</b> : 12XXXXX89<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class AccountNumber {

    @Size(max = 9)
    private String value;
}
