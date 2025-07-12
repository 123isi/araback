package co.kr.muldum.domain.budget.repository;

import co.kr.muldum.application.items.BudgetDetailsDto;
import co.kr.muldum.domain.budget.model.TeamBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamBudgetRepository extends JpaRepository<TeamBudget, Long> {

    @Query("SELECT new co.kr.muldum.application.items.BudgetDetailsDto(b.totalBudget, b.spendingBudget) "
            + "FROM TeamBudget b "
            + "WHERE b.teamId = :teamId")
    Optional<BudgetDetailsDto> findBudgetsById(Long teamId);


}
