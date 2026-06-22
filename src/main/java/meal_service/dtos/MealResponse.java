package meal_service.dtos;

import meal_service.models.Food;
import meal_service.models.Nutrient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record MealResponse(Long mealId,
                           Long userId,
                           List<Food> foodNames,
                           Float mealCalorie,
                           String mealType,
                           List<Nutrient> mealMacros,
                           LocalDate mealDate
                           ) {
}
