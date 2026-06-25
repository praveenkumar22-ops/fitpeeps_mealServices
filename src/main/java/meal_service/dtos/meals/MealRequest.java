package meal_service.dtos.meals;

import meal_service.dtos.foods.FoodItem;

import java.util.List;

public record MealRequest(String mealType,
                          List<FoodItem> foodItems) {
}
