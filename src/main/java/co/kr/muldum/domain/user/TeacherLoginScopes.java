package co.kr.muldum.domain.user;

import java.util.List;

public final class TeacherLoginScopes {

    private TeacherLoginScopes() {}

    public static final List<String> SCOPES = List.of(
            "id",
            "name",
            "email"
    );
}
