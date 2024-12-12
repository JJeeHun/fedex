package com.example.fedex_json.fedex.enums;

/**
 * <h2>PackagingType Information </h2>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">YOUR_PACKAGING</b> : [고객 포장재, FedEx Express® 서비스] 150 lbs/68 KG<br/>
 *   <li><b style="color: #86da98;">YOUR_PACKAGING</b> : [ FedEx Ground® 및 FedEx Home Delivery® ] 70 lbs/32 KG<br/>
 *   <li><b style="color: #86da98;">YOUR_PACKAGING</b> : [ 고객 포장재, FedEx Ground® Economy(이전 명칭: FedEx SmartPost®) 서비스 ] 70 lbs/32 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_ENVELOPE</b> : 1 lbs/0.5 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_BOX</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_SMALL_BOX</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_MEDIUM_BOX</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_LARGE_BOX</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_EXTRA_LARGE_BOX</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_10KG_BOX</b> : 22 lbs/10 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_25KG_BOX</b> : 55 lbs/25 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_PAK</b> : 20 lbs/9 KG<br/>
 *   <li><b style="color: #86da98;">FEDEX_TUBE</b> : 20 lbs/9 KG<br/>
 * </ul>
 */
public enum PackagingType {
    YOUR_PACKAGING,
    FEDEX_ENVELOPE,
    FEDEX_BOX,
    FEDEX_SMALL_BOX,
    FEDEX_MEDIUM_BOX,
    FEDEX_LARGE_BOX,
    FEDEX_EXTRA_LARGE_BOX,
    FEDEX_10KG_BOX,
    FEDEX_25KG_BOX,
    FEDEX_PAK,
    FEDEX_TUBE,
}
