package co.kr.muldum.domain.token;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private String userId;

    private String accessToken;

    public void updateToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
