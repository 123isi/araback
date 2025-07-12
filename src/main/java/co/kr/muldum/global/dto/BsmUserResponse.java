package co.kr.muldum.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BsmUserResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private String role;

    @JsonProperty("student")
    private Student student;

    @Getter
    public static class Student {

        @JsonProperty("grade")
        private int grade;

        @JsonProperty("class_no")
        private int classNo;

        @JsonProperty("student_no")
        private int studentNo;
    }
}
