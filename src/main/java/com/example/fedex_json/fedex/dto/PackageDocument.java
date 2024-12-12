package com.example.fedex_json.fedex.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">url</b> : https://wwwtest.fedex.com/document/v1/cache/retrieve/SH,4282a39351da955e794809858547_SHIPPING_L?isLabel=true&isLaser=true&autoPrint=false<br/></li>
 *   <li><b style="color: #86da98;">contentType</b> : LABEL<br/></li>
 *   <li><b style="color: #86da98;">copiesToPrint</b> : 1<br/></li>
 *   <li><b style="color: #86da98;">docType</b> : PNG<br/></li>
 *   <li><b style="color: #86da98;">encodedLabel</b> : iVBORw0KGgoAAAANS.....<br/></li>
 * </ul>
 * <p style="color: #ee6196;">* contentType이 Label일 경우 encodedLabel</p>
 */
@Builder @Jacksonized
@Getter @ToString
public class PackageDocument {
    private String url;
    private String contentType;
    private Integer copiesToPrint;
    private String docType;
    private String encodedLabel;
}
