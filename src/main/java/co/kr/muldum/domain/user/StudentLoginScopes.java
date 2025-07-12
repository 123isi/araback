package co.kr.muldum.domain.user;

import java.util.List;

public final class StudentLoginScopes {

    private StudentLoginScopes() {}

    public static final List<String> SCOPES = List.of(
            "id",
            "studentNo",
            "name",
            "enrolledAt",
            "email",
            "classNo",
            "grade",
            "isGraduate"
    );
}
