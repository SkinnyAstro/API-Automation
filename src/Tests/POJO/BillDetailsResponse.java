package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDetailsResponse {

    private String message;
    private String statusValue;
    private int statusCode;
    private int timeTakenInMs;
    private ResponseData responseData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData{
        private int orderId;
        private double tmCash;
        private double sellingPrice;
        private double payableAmt;
        private double discount;
        private double deliveryCharge;
        private double mrp;
    }


}
