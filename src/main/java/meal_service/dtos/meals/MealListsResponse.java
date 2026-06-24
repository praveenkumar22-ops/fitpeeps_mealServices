package meal_service.dtos.meals;

import java.util.List;

public record MealListsResponse(Long userId, List<MealResponse> meals) {
}
