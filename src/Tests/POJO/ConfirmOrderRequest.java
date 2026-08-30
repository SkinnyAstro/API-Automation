package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfirmOrderRequest {
    private String source = "WEBSITE";
    private String type = "Order";
    private int entityId;
    private boolean accepted = true;
    private String policy = "Communication policy";
    private String version = "TM_WEBSITE_V_4.4.1";


}
