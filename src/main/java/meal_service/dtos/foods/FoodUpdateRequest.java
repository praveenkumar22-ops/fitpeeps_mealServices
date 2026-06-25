package meal_service.dtos.foods;

public record FoodUpdateRequest(
        Double energy,
        Double protein,
        Double carbs,
        Double fats
) {
}
