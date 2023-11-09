package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBody {
    private String message;
    private String authority;
    private ResponseBody[] objects;
    private int id;
    private String description;
    private String responseBody;
    private String name;
    private String fullName;
}



