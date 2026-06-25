package meal_service.controllers;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.foods.FoodRequest;
import meal_service.dtos.foods.FoodUpdateRequest;
import meal_service.services.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
//this is specially for the admin panel
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodDetails(@PathVariable Long foodId){
        return new ResponseEntity<>(foodService.getFoodItem(foodId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerFood(@RequestBody FoodRequest request){
        return new ResponseEntity<>(foodService.registerFood(request),HttpStatus.OK);
    }

    @PostMapping("/{foodId}/delete")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId){
        return new ResponseEntity<>(foodService.deleteFoodItem(foodId),HttpStatus.OK);
    }

    @PostMapping("/{foodid}/update")
    public ResponseEntity<?> updateFood(@PathVariable Long foodId, @RequestBody FoodUpdateRequest request){
        return new ResponseEntity<>(foodService.updateFood(foodId,request),HttpStatus.OK);
    }
}
