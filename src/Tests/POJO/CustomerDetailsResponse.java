package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDetailsResponse {
    @JsonProperty("TmCash")
    private double tmCash;

    @JsonProperty("TmCredit")
    private double tmCredit;

}
