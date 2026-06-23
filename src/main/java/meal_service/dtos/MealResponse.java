package meal_service.dtos;

import meal_service.models.Food;
import meal_service.models.Nutrient;

import java.time.LocalDate;
import java.util.List;

public record MealResponse(Long mealId,
                           Long userId,
                           List<FoodItems> foodNames,
                           Float mealCalorie,
                           String mealType,
                           List<Nutrient> mealMacros,
                           LocalDate mealDate
                           ) {
}
