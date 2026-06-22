package meal_service.dtos;

import java.util.List;

public record MealListsResponse(Long userId, List<MealResponse> meals) {
}
