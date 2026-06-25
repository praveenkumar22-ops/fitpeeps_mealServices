package meal_service.services;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.foods.FoodItem;
import meal_service.dtos.meals.MealRequest;
import meal_service.dtos.meals.MealResponse;
import meal_service.models.Food;
import meal_service.models.Meal;
import meal_service.models.MealItem;
import meal_service.models.Nutrient;
import meal_service.models.enums.FoodType;
import meal_service.models.enums.MealType;
import meal_service.models.enums.Unit;
import meal_service.repos.MealItemRepo;
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
    private final MealItemRepo mealItemRepo;
    private final FoodService foodService;



    public MealResponse convertToMealResponse(Meal savedMeal,List<FoodItem> request){
        return new MealResponse(
                savedMeal.getMealId(),
                savedMeal.getUserId(),
                request,
                savedMeal.getMealCalorie(),
                savedMeal.getMealType().toString(),
                savedMeal.getMealMacros(),
                savedMeal.getMealDate()
        );
    }
    public Food findFood(FoodItem foodItems){
        return foodService.getFoodDetails(foodItems.foodId()).get();
    }

    public void setNutrient(Nutrient nutrient,String name,Double amount){
         nutrient.setName(name);
         nutrient.setValue(amount);
    }

    public Optional<MealResponse> registerMealForUser(Long userId, MealRequest request) {

        // we need to take out the food from the food items list and then put into a new list that contain those food items.
//        List<Food> inputFoodItems=new ArrayList<Food>();
        List<Nutrient> inputMealMacros=new ArrayList<Nutrient>(3);
//                request.foodItems();
        Double proteinContent=0.0;
        Double fatsContent=0.0;
        Double carbsContent=0.0;
        Double totalCalorie=0.0;
        Meal newMeal=new Meal();
        newMeal.setUserId(userId);
        newMeal.setMealDate(LocalDate.now());
        newMeal.setMealType(MealType.valueOf(request.mealType()));
        List<MealItem> curMealItems=new ArrayList<>();
        for(FoodItem food: request.foodItems()){
            MealItem newMealItem=new MealItem();
            Food curFood=findFood(food);
            Double quantity=food.quantity();
            proteinContent+=((quantity/100.0 ) * curFood.getProtein());
            fatsContent+=((quantity/100.0 ) * curFood.getFats());
            carbsContent+=((quantity/100.0 ) * curFood.getCarbs());
            totalCalorie+=(quantity/100.0) * curFood.getEnergy();
            newMealItem.setMeal(newMeal);
            newMealItem.setFood(curFood);
            newMealItem.setQuantity(quantity);
            newMealItem.setFoodType(FoodType.valueOf(food.foodType()));
            curMealItems.add(newMealItem);
//            inputFoodItems.add(curFood);
        }
        setNutrient(inputMealMacros.get(0),"Protein",proteinContent);
        setNutrient(inputMealMacros.get(1),"Fats",fatsContent);
        setNutrient(inputMealMacros.get(2),"Carbs",carbsContent);
        newMeal.setMealCalorie(totalCalorie);
        newMeal.setMealMacros(inputMealMacros);
        mealItemRepo.saveAll(curMealItems);
        return Optional.of(convertToMealResponse(newMeal,request.foodItems()));
    }

    public Optional<MealResponse> getMealDetails(Long mealId) {
        List<MealItem> foundMeals=mealItemRepo.findAllBYMeal_MealId(mealId);
        Optional<Meal> foundMeal= mealRepo.findById(mealId);
        List<FoodItem> foundFoodItems=new ArrayList<>();
        foundMeals.forEach(
                foodItem->{
                    Food foundFood=foodItem.getFood();
                    foundFoodItems.add(new FoodItem(foundFood.getFoodId()
                            ,foundFood.getName(),
                            foodItem.getQuantity(),
                            foodItem.getFoodType().toString()
                            ));
                }
        );
        return foundMeal.map(meal -> convertToMealResponse(meal, foundFoodItems));
    }

    public List<MealResponse> getAllMealsForUser(Long userId){
        return mealRepo.findByUserId(userId)
                .stream()
                .map(
                        meal-> getMealDetails(meal.getMealId()).get()
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
                        meal-> getMealDetails(meal.getMealId()).get()
                ).toList();
    }
}
