package com.example.fedex_json.fedex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
@Getter @ToString
public class Parameter {
    private String key;
    private String value;
}
