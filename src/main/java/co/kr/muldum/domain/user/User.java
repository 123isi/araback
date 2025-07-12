package co.kr.muldum.domain.user;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String email;
    private String name;
    private Integer grade;
    private Integer classNo;
    private Role role;
    private Integer studentNo;
    private boolean isGraduate;
    private Integer enrolledAt;
    private Integer cardinal;
    private String profileUrl;

     public enum Role{
         STUDENT,
         TEACHER,
         ADMIN
     };
}
