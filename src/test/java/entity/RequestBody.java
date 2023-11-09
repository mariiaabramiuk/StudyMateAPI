package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class RequestBody {


    private String email;
    private String password;
    private int imageId;
    private String groupName;
    private String dateOfFinish;
    private String description;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private List<Integer> courses;
    private int groupId;
    private String studyFormat;
    private String studentId;

}
