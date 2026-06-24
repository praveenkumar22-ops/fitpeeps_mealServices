package meal_service.controllers;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.meals.MealRequest;
import meal_service.dtos.meals.MealResponse;
import meal_service.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/{userId}/register")
    public ResponseEntity<?> registerMealForUser(@PathVariable Long userId, @RequestBody MealRequest request){
        Optional<MealResponse> savedMeal=mealService.registerMealForUser(userId,request);
        return new ResponseEntity<>(savedMeal, HttpStatus.OK);
    }


    @GetMapping("/{userId}/{mealId}")
    public ResponseEntity<?> getMealDetails(@PathVariable Long userId, @PathVariable Long mealId){
        Optional<MealResponse> foundMeal=mealService.getMealDetails(userId,mealId);

        return foundMeal.isPresent() ?
                new ResponseEntity<>(foundMeal.get(),HttpStatus.OK):
                new ResponseEntity<>("No such meals found",HttpStatus.OK);
    }

}
