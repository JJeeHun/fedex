package com.example.fedex_json.fedex.dto;

import com.example.fedex_json.fedex.enums.ImageType;
import com.example.fedex_json.fedex.enums.LabelFormatType;
import com.example.fedex_json.fedex.enums.LabelStockType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">sequenceNumber</b> : 1<br/></li>
 *   <li><b style="color: #86da98;">subPackagingType</b> : {@link LabelStockType}<br/></li>
 *          <a href="https://developer.fedex.com/api/ko-kr/guides/api-reference.html#subpackagetypes">Reference</a></li>
 *   <li><b style="color: #86da98;">customerReferences</b> : List<{@link CustomerReference}><br/></li>
 *   <li><b style="color: #ee6196;">weight</b> : {@link Weight}<br/></li>
 *   <li><b style="color: #86da98;">dimensions</b> : {@link Dimensions}<br/></li>
 *   <li><b style="color: #86da98;">groupPackageCount</b> : 2<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class RequestedPackageLineItem {
    private Integer sequenceNumber;
    private String subPackagingType;
    private List<CustomerReference> customerReferences;
    @NotNull
    private Weight weight;
    private Dimensions dimensions;
    private Integer groupPackageCount;
}

