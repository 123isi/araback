package co.kr.muldum.domain.budget.service;

import co.kr.muldum.application.items.BudgetDetailsDto;
import co.kr.muldum.domain.budget.repository.TeamBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamBudgetService {

    private final TeamBudgetRepository teamBudgetRepository;

    public BudgetDetailsDto getBudgetDetails(Long teamId) {
        return teamBudgetRepository.findBudgetsById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀의 예산 정보가 존재하지 않습니다."));
    }

}
