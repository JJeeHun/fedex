package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.UnitLengthType;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">length</b> : 20<br/>
 *   <li><b style="color: #86da98;">width</b> : 10<br/>
 *   <li><b style="color: #86da98;">height</b> : 10<br/>
 *   <li><b style="color: #86da98;">units</b> : {@link UnitLengthType}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Dimensions {
    @Max(999)
    private Integer length;
    @Max(999)
    private Integer width;
    @Max(999)
    private Integer height;
    private UnitLengthType units;
}
