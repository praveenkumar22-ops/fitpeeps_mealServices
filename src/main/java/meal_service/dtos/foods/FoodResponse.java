package meal_service.dtos.foods;

public record FoodResponse (
        Long foodId,
        String name,
        String foodType,
        Double energy,
        Double protein,
        Double fats,
        Double carbs
){
}
