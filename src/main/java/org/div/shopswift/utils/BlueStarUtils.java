package org.div.shopswift.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * 處理 藍星金流相關工具
 */
public class BlueStarUtils {
    /**
     * 生成CheckValue
     * */
    public static String generateCheckValue(String merchantId, String amt, String merchantOrderNo , String iv, String key) {

        TreeMap<String, String> sortedCheckCode = new TreeMap<>();
        sortedCheckCode.put("Amt",amt);
        sortedCheckCode.put("MerchantID",merchantId);
        sortedCheckCode.put("MerchantOrderNo",merchantOrderNo);
        StringBuilder checkStr = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : sortedCheckCode.entrySet()) {
                if (checkStr.length() != 0) {
                    checkStr.append("&");
                }
                checkStr.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String checkValue = String.format("IV=%s&%s&Key=%s",iv,checkStr.toString(),key);
        String hashCheckValue = SHA256Utils.sha256(checkValue).toUpperCase();
        return hashCheckValue;
    }
}
