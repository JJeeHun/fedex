package com.example.fedex_json;

import com.example.fedex_json.fedex.CreateShipDto;
import com.example.fedex_json.fedex.FedexService;
import com.example.fedex_json.fedex.enums.ServiceType;
import com.example.fedex_json.fedex.enums.USStateCodeType;
import com.example.fedex_json.fedex.shipment.ShipmentCancelResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FedexController {

    private final FedexService fedexService;
    private final ObjectMapper om;
    private final String ACCOUNT_NUMBER = "740561073";

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/fedex/create")
    public String createFedexView() {
        return "fedex";
    }

    @PostMapping("/fedex/create")
    @ResponseBody
    public Map<String, Object> create(@RequestParam("whOrderNo") String whOrderNo,
                                      @RequestParam("orderNo") String orderNo,
                                      @RequestParam("jobG") String jobG) {

        return fedexService.fedexSearchAndCreateShipment(whOrderNo, orderNo, jobG);
    }

    @GetMapping("/fedex/custom-create")
    public String createCustomView(Model model) {
        model.addAttribute("serviceTypes", ServiceType.values());
        model.addAttribute("stateCodeTypes", USStateCodeType.values());
        model.addAttribute("ship", CreateShipDto.builder()
            .serviceType(ServiceType.FEDEX_GROUND)
            .thirdPartyAccount("740561073")
            .totalWeight(10d)
            .recipientPerson("ASHLEY JORGENSEN")
            .recipientCompany("DIAMOND L DESIGN")
            .recipientPhone("8017988591")
            .recipientStreetLines(Arrays.asList("1283 EXPRESSWAY LANE",""))
            .recipientCity("SPANISH FORK")
            .recipientStateOrProvinceCode(USStateCodeType.UT)
            .recipientPostalCode("84660")
            .seq(1)
            .packageWeight(3)
            .length(16)
            .width(7)
            .height(9)
            .invoice("SO24006762123213")
            .po("1983333")
            .isThirdParty(false)
            .build());
        return "fedex_custom";
    }

    @PostMapping("/fedex/custom-create")
    public String createCustom(@ModelAttribute CreateShipDto dto, RedirectAttributes model) throws JsonProcessingException {
        dto.setRootAccount(ACCOUNT_NUMBER); // TODO - 계정 AccountNumber

        Map<String, Object> stringObjectMap = fedexService.fedexCreateShipment(Arrays.asList(dto));

        model.addFlashAttribute("result", om.writeValueAsString(stringObjectMap));

        return "redirect:/fedex/custom-create";
    }

    @GetMapping("/fedex/cancel")
    public String cancelShipmentView() {
        return "fedex_cancel";
    }

    @PostMapping("/fedex/cancel")
    public String cancelShipment(String trackingNumber ,RedirectAttributes model) throws JsonProcessingException {
        ShipmentCancelResponse shipmentCancelResponse = fedexService.cancelShipment(ACCOUNT_NUMBER, trackingNumber);

        model.addFlashAttribute("result", om.writeValueAsString(shipmentCancelResponse));

        return "redirect:/fedex/cancel";
    }

}
