package com.switchwon.paymentimpl.avro;

import com.switchwon.avro.EstimateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AvroDtoTest {

    @DisplayName("AVRO 동등성, 동일성 테스트")
    @Test
    public void testDto(){
        EstimateRequestDto estimateRequestDto = new EstimateRequestDto(new BigDecimal("12.34"), "USDT", "id123", "12345");
        EstimateRequestDto estimateRequestDto2 = new EstimateRequestDto(new BigDecimal("12.34"), "USDT", "id123", "12345");

        System.out.println("estimateRequestDto = " + estimateRequestDto);
        System.out.println("estimateRequestDto2 = " + estimateRequestDto2);
        System.out.println(estimateRequestDto== estimateRequestDto2);
        System.out.println(estimateRequestDto.equals(estimateRequestDto2));

        Assertions.assertEquals(estimateRequestDto, estimateRequestDto2);
        Assertions.assertNotSame(estimateRequestDto, estimateRequestDto2);

    }
}
