package com.example.fedex_json.fedex.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder @Jacksonized
@Getter @ToString
public class Error {
    private String code;
    private List<Parameter> parameterList;
    private String message;
}
