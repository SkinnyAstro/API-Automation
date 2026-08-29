package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data // tells Lambork to generate getters and setters
@JsonIgnoreProperties(ignoreUnknown = true) // basically a field in JSON is not declared the just ignore and don't throw an error
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
