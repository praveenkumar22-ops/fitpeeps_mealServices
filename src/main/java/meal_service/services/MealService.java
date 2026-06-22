package meal_service.services;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.MealRequest;
import meal_service.dtos.MealResponse;
import meal_service.models.Meal;
import meal_service.repos.MealRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepo mealRepo;

    public MealResponse convertToMealResponse(Meal savedMeal){
        return new MealResponse(
                savedMeal.getMealId(),
                savedMeal.getUserId(),
                savedMeal.getFoodNames(),
                savedMeal.getMealCalorie(),
                savedMeal.getMealType(),
                savedMeal.getMealMacros(),
                savedMeal.getMealDate()
        );
    }

    public Optional<MealResponse> registerMealForUser(Long userId, MealRequest request) {
        Meal newMeal=new Meal();
        newMeal.setUserId(userId);
        newMeal.setMealDate(LocalDate.now());
        newMeal.setMealCalorie(request.mealCalorie());
        newMeal.setFoodNames(request.foodNames());
        newMeal.setMealType(request.mealType());
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
