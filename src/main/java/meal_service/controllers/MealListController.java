package meal_service.controllers;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.meals.MealListsResponse;
import meal_service.dtos.meals.MealResponse;
import meal_service.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealListController {
    private final MealService mealService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMeals(){
        List<MealResponse> allMeals=mealService.getAllMeals();
        return new ResponseEntity<>(allMeals,HttpStatus.OK);
    }

    @GetMapping("/{userId}/date/{Date}")
    public ResponseEntity<?> getAllMealsForTheDate(@PathVariable Long userId, @PathVariable LocalDate Date){
        List<MealResponse> foundMeals=mealService.getAllMealsForTheDate(userId,Date);
        MealListsResponse meals=new MealListsResponse(userId,foundMeals);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/{userid}/month/{month}")
    public ResponseEntity<?> getAllMealsForTheMonth(@PathVariable Long userId,@PathVariable Integer month){
        List<MealResponse> foundMeals=mealService.getAllMealForTheMonth(userId,month);
        MealListsResponse meals=new MealListsResponse(userId,foundMeals);
        return new ResponseEntity<>(meals,HttpStatus.OK);
    }
}
