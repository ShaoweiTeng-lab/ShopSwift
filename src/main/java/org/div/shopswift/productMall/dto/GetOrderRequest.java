package org.div.shopswift.productMall.dto;

import lombok.Data;

/**
 * 獲得訂單Request
 * */
@Data
public class GetOrderRequest {
    private String amt;
    private String merchantOrderNo;
    private String tradeNo ;
}
