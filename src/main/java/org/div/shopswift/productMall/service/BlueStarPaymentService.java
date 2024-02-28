package org.div.shopswift.productMall.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.div.shopswift.productMall.dto.*;
import org.div.shopswift.utils.AES256CBCUtils;
import org.div.shopswift.utils.BlueStarUtils;
import org.div.shopswift.utils.SHA256Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 藍星金流 Service
 * */

@Service
public class BlueStarPaymentService {
    @Value(value = "${key}")
    private String key;
    @Value(value = "${iv}")
    private String iv;
    @Value(value = "${mid}")
    private String mid;
    @Value(value = "${notifyURL}")
    private String notifyURL;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getSHAEncrypt() {
        try {
            return AES256CBCUtils.encrypt(mid, key, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成訂單
     */
    public FormResponse createOrderInfo(FormRequest formRequest) {
        long t = System.currentTimeMillis();
        formRequest.setMerchantID(mid);
        formRequest.setRespondType("String");
        formRequest.setNotifyURL(notifyURL);
        formRequest.setVersion("2.0");
        formRequest.setTimeStamp(t);
        formRequest.setMerchantOrderNo("test_store_Mpg_" + t);
        formRequest.setCVSCOM(0);//關閉物流
        String tradeInfo = null;
        try {
            //將formData 進行 AES256CBC 加密
            System.out.println(formRequest.toFormDataString());
            tradeInfo = AES256CBCUtils.encrypt(formRequest.toFormDataString(), key, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //依照藍星金流格式串接  StringUpper(sha256(HashKey=key&tradeInfo&HashIV))
        String tradeSha = SHA256Utils.sha256("HashKey=" + key + "&" + tradeInfo + "&" + "HashIV=" + iv).toUpperCase();
        FormResponse formResponse = new FormResponse();
        formResponse.setMerchantID(mid);
        formResponse.setTradeInfo(tradeInfo);
        formResponse.setTradeSha(tradeSha);
        formResponse.setVersion(2.0);
        //將加密資料返回前端後 由前端對藍星發請求,之後倒轉轉頁到藍星付款頁
        return formResponse;
    }

    /**
     * 生成物流訂單
     */
    public FormResponse createOrderLogisticInfo(FormRequest formRequest) {
        long t = System.currentTimeMillis();
        formRequest.setMerchantID(mid);
        formRequest.setRespondType("String");
        formRequest.setNotifyURL(notifyURL);
        formRequest.setVersion("2.0");
        formRequest.setTimeStamp(t);
        formRequest.setMerchantOrderNo("test_store_Mpg_" + t);
        formRequest.setCVSCOM(2);//啟用物流超商付款 , 若不註解此行則 付款後取貨
        String tradeInfo = null;
        try {
            //將formData 進行 AES256CBC 加密
            System.out.println(formRequest.toFormDataString());
            tradeInfo = AES256CBCUtils.encrypt(formRequest.toFormDataString(), key, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //依照藍星金流格式串接  StringUpper(sha256(HashKey=key&tradeInfo&HashIV))
        String tradeSha = SHA256Utils.sha256("HashKey=" + key + "&" + tradeInfo + "&" + "HashIV=" + iv).toUpperCase();
        FormResponse formResponse = new FormResponse();
        formResponse.setMerchantID(mid);
        formResponse.setTradeInfo(tradeInfo);
        formResponse.setTradeSha(tradeSha);
        formResponse.setVersion(2.0);
        //將加密資料返回前端後 由前端對藍星發請求,之後倒轉轉頁到藍星付款頁
        return formResponse;
    }

    /**
     * 處理藍星付款後返回參數
     */
    public String processResponse(TransactionResponse data) {
        String newData = null;
        try {
            newData = AES256CBCUtils.decrypt(data.getTradeInfo(), key, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Utils.removePadding(data.getTradeInfo());
        System.out.println(newData);
        return newData;
    }

    /**
     * 查詢訂單
     * */
    public GetOrderResponse queryOrder(GetOrderRequest getOrderRequest){
        String checkValue = BlueStarUtils.generateCheckValue(mid, getOrderRequest.getAmt(), getOrderRequest.getMerchantOrderNo(), iv, key);


        String url = "https://ccore.newebpay.com/API/QueryTradeInfo";
        // 設置表單數據
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("MerchantID", mid);
        formData.add("Version", "2.0");
        formData.add("RespondType", "JSON");
        formData.add("CheckValue", checkValue);
        formData.add("TimeStamp", String.valueOf(System.currentTimeMillis()));
        formData.add("MerchantOrderNo", getOrderRequest.getMerchantOrderNo());
        formData.add("Amt", getOrderRequest.getAmt());

        // 設置標頭
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        // 創建HttpEntity以包含表單數據和標頭
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        // 發送POST請求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request successful");
            System.out.println("Response body: " + response.getBody());
            GetOrderResponse rs = null;
            try {
                rs = objectMapper.readValue(response.getBody() , GetOrderResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return  rs;
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return null;
        }

    }

}
