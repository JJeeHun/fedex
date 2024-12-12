package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.CountryCodeType;
import com.example.fedex_json.fedex.enums.USStateCodeType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98 ]
/**
 * <b style="font-size:15px;">Address Information</b>
 * <br/>
 * <br/>
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">streetLines</b> : [1550 Union Blvd, Suite 302] [ List<{@link String}> ]</li>
 *   <li><b style="color: #ee6196;">city</b> : Beverly Hills</li>
 *   <li><b style="color: #86da98;">stateOrProvinceCode</b> : TX</li>
 *   <li><b style="color: #86da98;">postalCode (우편번호)</b> : 65247<br/>
 *       <pre style="font-size:8px">      <i>Reference:</i> <a style="color: #4883f4;" href="https://developer.fedex.com/api/ko-kr/guides/api-reference.html#postalawarecountries">Postal Code Guide</a></li></pre>
 *   <li><b style="color: #ee6196;">countryCode (국가코드)</b> : US<br/>
 *       <pre style="font-size:8px">      <i>Reference:</i> <a style="color: #4883f4;" href="https://developer.fedex.com/api/ko-kr/guides/api-reference.html#countrycodes">Country Code Guide</a></li></pre>
 *   <li><b style="color: #86da98;">residential (주거용)</b> : false</li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Address {


    @NotNull
    @Size(max = 35)
    private List<String> streetLines;

    @NotNull
    @Size(max = 35)
    private String city;

    @Size(max = 2)
    private USStateCodeType stateOrProvinceCode;

    @Size(max = 10)
    private String postalCode;

    @NotNull
    @Size(max = 2)
    private CountryCodeType countryCode;

    private Boolean residential;
}
