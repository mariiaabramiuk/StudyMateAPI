package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

// The main class representing the response from the API
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentResponse {

    // List of StudentInfo objects in the response
    private List<StudentInfo> objects;

    // This is a nested class representing information about a student
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StudentInfo {

        // Student ID
        private int studentId;

        // Full name of the student
        private String fullName;

        // Group name (can be null)
        private String groupName;

        // Study format (e.g., ONLINE)
        private String studyFormat;

        // Phone number of the student
        private String phoneNumber;

        // Email address of the student
        private String email;

        // Indicates whether the student is blocked
        private boolean isBlocked;
    }
}
