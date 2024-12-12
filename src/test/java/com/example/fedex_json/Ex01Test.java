package com.example.fedex_json;

import com.example.fedex_json.fedex.enums.UnitWeightType;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class Ex01Test {

    @Test
    void test01() {
        List<String> list = null;
        System.out.println(CollectionUtils.isEmpty(list));
    }

    @Test
    void enumTest() {
        System.out.println(UnitWeightType.LB.equals(UnitWeightType.valueOf("LB")));
    }
}
