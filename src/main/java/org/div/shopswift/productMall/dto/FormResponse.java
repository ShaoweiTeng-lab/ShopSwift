package org.div.shopswift.productMall.dto;

import lombok.Data;

@Data
public class FormResponse {
    private String  merchantID;
    private String  tradeInfo;
    private  String tradeSha;
    private  double version;
}
