package com.retailx.dispatch.dto;
import java.math.BigDecimal;

public class OrderEvent {

    public Long id;
    public Long clientId;
    public String status;
    public BigDecimal total;
    public String createdAt;
}
