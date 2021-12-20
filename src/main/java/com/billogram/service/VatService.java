package com.billogram.service;

import java.math.BigDecimal;

public class VatService {
    public BigDecimal getPriceBeforeTax(BigDecimal vat) {
        return vat.multiply(new BigDecimal("0.75")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getVatAmount(BigDecimal vat) {
        return vat.multiply(new BigDecimal("0.25")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
