package meal_service.services;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.foods.FoodRequest;
import meal_service.dtos.foods.FoodResponse;
import meal_service.dtos.foods.FoodUpdateRequest;
import meal_service.models.Food;
import meal_service.models.enums.FoodType;
import meal_service.models.enums.Unit;
import meal_service.repos.FoodRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepo foodRepo;

    public FoodResponse convertToFoodItemResponse(Food savedFood){
        return new FoodResponse(
                savedFood.getFoodId(),
                savedFood.getName(),
                savedFood.getFoodType().toString(),
                savedFood.getEnergy(),
                savedFood.getProtein(),
                savedFood.getFats(),
                savedFood.getCarbs()
        );
    }

    public Food convertRequestToFood(FoodRequest request){
        Food newFood=new Food();
        newFood.setName(request.name());
        newFood.setFoodType(FoodType.valueOf(request.foodType()));
        newFood.setEnergy(request.energy());
        newFood.setProtein(request.protein());
        newFood.setFats(request.fats());
        newFood.setCarbs(request.carbs());
        if(newFood.getFoodType().toString().equalsIgnoreCase("liquid")){
            newFood.setUnit(Unit.LITRES);
        }
        else newFood.setUnit(Unit.GRAMS);
        return newFood;
    }

    public Optional<FoodResponse> registerFood(FoodRequest request){
        Food newFood=convertRequestToFood(request);
        foodRepo.save(newFood);
        return Optional.of(convertToFoodItemResponse(newFood));
    }

    // update the values
    // either for the admin
    // we only allow limited updates on the food
    public Optional<FoodResponse> updateFood(Long foodId, FoodUpdateRequest updateRequest){
        Optional<Food> foundFood= getFoodDetails(foodId);
        if(foundFood.isEmpty())return Optional.empty();
        Food getFood=foundFood.get();
        getFood.setEnergy(updateRequest.energy());
        getFood.setProtein(updateRequest.protein());
        getFood.setFats(updateRequest.fats());
        getFood.setCarbs(updateRequest.carbs());
        Food savedFood=foodRepo.save(getFood);
        return Optional.of(convertToFoodItemResponse(savedFood));
    }

    // get the food
    public Optional<FoodResponse> getFoodItem(Long foodId){
        return foodRepo.findById(foodId).map(this::convertToFoodItemResponse);
    }

    // delete the food
    public Optional<FoodResponse> deleteFoodItem(Long foodId){
        Optional<Food> deletedFood= getFoodDetails(foodId);
        if(deletedFood.isEmpty())return Optional.empty();
        Food getFood=deletedFood.get();
        foodRepo.delete(getFood);
        return Optional.of(convertToFoodItemResponse(getFood));
    }

    public Optional<Food> getFoodDetails(Long foodId){
        return foodRepo.findById(foodId);
    }

}
