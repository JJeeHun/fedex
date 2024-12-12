package com.example.fedex_json.fedex;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface FedexRepository {

    @Select("begin exec up_UPS_FEDEX_SEND @TYPEDIV = 'UPS_FEDEX'" +
            ", @WH_ORD_NO = #{whOrdNo}" +
            ", @ORD_NO = #{ordNo}" +
            ", @JOB_G = #{jobG}" +
            "end;")
    public List<Map<String,Object>> findShipping(String whOrdNo, String ordNo, String jobG);
}
