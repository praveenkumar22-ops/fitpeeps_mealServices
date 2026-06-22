package meal_service.dtos;

import meal_service.models.Food;

import java.util.List;
import java.util.Map;

public record MealRequest(String mealType, List<Food> foodNames, Float mealCalorie) {
}
