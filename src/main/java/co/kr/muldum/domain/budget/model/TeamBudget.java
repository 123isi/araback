package co.kr.muldum.domain.budget.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_budget")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class TeamBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long teamId;

    @Column(name= "total_budget_krw0f")
    private Long totalBudget;

    @Column(name= "spending_budget_krw0f")
    private Long spendingBudget;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.totalBudget == null) {
            this.totalBudget = 0L;
        }
        if (this.spendingBudget == null) {
            this.spendingBudget = 0L;
        }
    }
}
