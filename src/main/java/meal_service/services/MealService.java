package meal_service.services;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.FoodItems;
import meal_service.dtos.MealRequest;
import meal_service.dtos.MealResponse;
import meal_service.models.Food;
import meal_service.models.Meal;
import meal_service.models.Nutrient;
import meal_service.models.Unit;
import meal_service.repos.FoodRepo;
import meal_service.repos.MealRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepo mealRepo;
    private final FoodRepo foodRepo;

    public FoodItems covertToFoodItems(Food foodItems){
        return  new FoodItems(foodItems.getFoodId(),foodItems.getName(),foodItems.getQuantity(),foodItems.getUnit().toString());
    }


    public MealResponse convertToMealResponse(Meal savedMeal){
        List<FoodItems> foodItems=savedMeal.getFoodNames()
                .stream()
                .map(
                        this::covertToFoodItems
                )
                .toList();
        return new MealResponse(
                savedMeal.getMealId(),
                savedMeal.getUserId(),
                foodItems,
                savedMeal.getMealCalorie(),
                savedMeal.getMealType(),
                savedMeal.getMealMacros(),
                savedMeal.getMealDate()
        );
    }
    public Food findFood(FoodItems foodItems){
        return foodRepo.findById(foodItems.foodId()).get();
    }

    public void setNutrient(Nutrient nutrient,String name,Double amount,Enum unit){
         nutrient.setName(name);
         nutrient.setValue(amount);
         nutrient.setUnit(unit);
    }

    public Optional<MealResponse> registerMealForUser(Long userId, MealRequest request) {

        // we need to take out the food from the food items list and then put into a new list that contain those food items.
        List<Food> inputFoodItems=new ArrayList<Food>();
        List<Nutrient> inputMealMacros=new ArrayList<Nutrient>(3);
//                request.foodItems();
        Double proteinContent=0.0;
        Double fatsContent=0.0;
        Double carbsContent=0.0;

        for(FoodItems food: request.foodItems()){
            Food curFood=findFood(food);
            Double quantity=food.quantity();
            proteinContent+=((quantity/100.0 ) * curFood.getProteinPer100g());
            fatsContent+=((quantity/100.0 ) * curFood.getFatsPer100g());
            carbsContent+=((quantity/100.0 ) * curFood.getCarbsPer100g());
            inputFoodItems.add(curFood);
        }
        setNutrient(inputMealMacros.get(0),"Protein",proteinContent,Unit.GRAMS);
        setNutrient(inputMealMacros.get(0),"Fats",fatsContent,Unit.GRAMS);
        setNutrient(inputMealMacros.get(0),"Carbs",carbsContent,Unit.GRAMS);

        Meal newMeal=new Meal();
        newMeal.setUserId(userId);
        newMeal.setMealDate(LocalDate.now());
        newMeal.setMealCalorie(request.mealCalorie());
        newMeal.setFoodNames(inputFoodItems);
        newMeal.setMealType(request.mealType());
        newMeal.setMealMacros(inputMealMacros);
        return Optional.of(convertToMealResponse(newMeal));
    }

    public Optional<MealResponse> getMealDetails(Long userId, Long mealId) {
        Optional<Meal> foundMeal= mealRepo.findById(mealId);
        return foundMeal.isPresent() ?
                Optional.of(convertToMealResponse(foundMeal.get())) :
                Optional.empty();
    }

    public List<MealResponse> getAllMealsForUser(Long userId){
        return mealRepo.findByUserId(userId)
                .stream()
                .map(
                        this::convertToMealResponse
                )
                .toList();
    }

    public List<MealResponse> getAllMealsForTheDate(Long userId, LocalDate date) {
        List<MealResponse> foundMeals=getAllMealsForUser(userId);
        return foundMeals.stream().filter(
                activity -> activity.mealDate()==date
        ).toList();
    }

    public List<MealResponse> getAllMealForTheMonth(Long userId, Integer month) {
        List<MealResponse> foundMeals=getAllMealsForUser(userId);
        return foundMeals.stream()
                .filter(
                        activity -> activity.mealDate().getMonthValue()==month
                ).toList();
    }

    public List<MealResponse> getAllMeals() {
        return mealRepo.findAll()
                .stream()
                .map(
                        this::convertToMealResponse
                ).toList();
    }
}
