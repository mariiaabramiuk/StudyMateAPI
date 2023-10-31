package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class RequestBody {
    private String email;
    private String password;
    private String imageId;
    private String groupName;
    private String dateOfFinish;
    private String description;
}
