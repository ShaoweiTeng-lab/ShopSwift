package org.div.shopswift.productMall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 獲得訂單Response
 */
@Data
public class GetOrderResponse {
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Result")
    private Result result;

    public  class Result {
        @JsonProperty("MerchantID")
        private String merchantID;
        @JsonProperty("Amt")
        private String amt;
        @JsonProperty("TradeNo")
        private String tradeNo;
        @JsonProperty("MerchantOrderNo")
        private String merchantOrderNo;
        @JsonProperty("TradeStatus")
        private String tradeStatus;
        @JsonProperty("PaymentType")
        private String paymentType;
        @JsonProperty("CreateTime")
        private String createTime;
        @JsonProperty("PayTime")
        private String payTime;
        @JsonProperty("FundTime")
        private String fundTime;
        @JsonProperty("CheckCode")
        private String checkCode;
        @JsonProperty("RespondCode")
        private String respondCode;
        @JsonProperty("Auth")
        private String auth;
        @JsonProperty("ECI")
        private String ECI;
        @JsonProperty("CloseAmt")
        private String closeAmt;
        @JsonProperty("CloseStatus")
        private String closeStatus;
        @JsonProperty("BackBalance")
        private String backBalance;
        @JsonProperty("BackStatus")
        private String backStatus;
        @JsonProperty("RespondMsg")
        private String respondMsg;
        @JsonProperty("Inst")
        private String inst;
        @JsonProperty("InstFirst")
        private String instFirst;
        @JsonProperty("InstEach")
        private String instEach;
        @JsonProperty("PaymentMethod")
        private String paymentMethod;
        @JsonProperty("Card6No")
        private String card6No;
        @JsonProperty("Card4No")
        private String card4No;
        @JsonProperty("AuthBank")
        private String authBank;

    }


}





