package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.PackagingType;
import com.example.fedex_json.fedex.enums.UnitWeightType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">units</b> : {@link UnitWeightType}<br/>
 *   <li><b style="color: #ee6196;">value</b> : 68.25 [ {@link PackagingType} ]<br/>
 *     <a href="https://developer.fedex.com/api/en-us/guides/api-reference.html#packagetypes">Reference</a>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Weight {

    @NotNull
    private UnitWeightType units;

    @NotNull
    private Double value;
}
