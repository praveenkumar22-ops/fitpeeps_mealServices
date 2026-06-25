package meal_service.dtos.meals;

import meal_service.dtos.foods.FoodItem;
import meal_service.models.Nutrient;

import java.time.LocalDate;
import java.util.List;

public record MealResponse(Long mealId,
                           Long userId,
                           List<FoodItem> foodItems,
                           Double mealCalorie,
                           String mealType,
                           List<Nutrient> mealMacros,
                           LocalDate mealDate
                           ) {
}
