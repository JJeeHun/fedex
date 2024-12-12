package com.example.fedex_json.fedex;

import com.example.fedex_json.fedex.enums.ServiceType;
import com.example.fedex_json.fedex.enums.USStateCodeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter @ToString
public class CreateShipDto {

    private ServiceType serviceType;
    private String thirdPartyAccount;
    @Setter private String rootAccount;
    private Double totalWeight;

    private String recipientPerson;
    private String recipientCompany;
    private String recipientPhone;
    private List<String> recipientStreetLines;
    private String recipientCity;
    private USStateCodeType recipientStateOrProvinceCode;
    private String recipientPostalCode;

    private int seq;
    private int packageCount;
    private double packageWeight;
    private int length;
    private int width;
    private int height;
    private String invoice;
    private String po;

    private Boolean isThirdParty;

    public boolean isThirdParty() {
        return isThirdParty != null && isThirdParty;
    }
}
