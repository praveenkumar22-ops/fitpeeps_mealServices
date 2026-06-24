package meal_service.dtos.foods;

public record FoodRequest(String name,
                          String foodType,
                          Double energy,
                          Double protein,
                          Double fats,
                          Double carbs
) {
}
