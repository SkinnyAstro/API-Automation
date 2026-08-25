package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyRewardsResponse {

    private String message;
    private String statusValue;
    private int statusCode;
    private int timeTakenInMs;
    private ResponseData responseData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData{
        private boolean calculateTmRewards;
    }

}
