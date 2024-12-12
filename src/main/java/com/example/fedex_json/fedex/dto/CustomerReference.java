package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.CustomerReferenceType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">customerReferenceType</b> : {@link CustomerReferenceType}<br/>
 *   <a href="https://developer.fedex.com/api/ko-kr/guides/api-reference.html#customerreferencetypes">Reference</a>
 *   <li><b style="color: #86da98;">value</b> : 3686<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CustomerReference {
    private CustomerReferenceType customerReferenceType;
    private String value;
}
