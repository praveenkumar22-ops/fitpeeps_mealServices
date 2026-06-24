package meal_service.services;

import lombok.RequiredArgsConstructor;
import meal_service.dtos.foods.FoodRequest;
import meal_service.dtos.foods.FoodResponse;
import meal_service.dtos.foods.FoodUpdateRequest;
import meal_service.models.Food;
import meal_service.models.FoodType;
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
                savedFood.getEnergyPer100g(),
                savedFood.getEnergyPer100ml(),
                savedFood.getProteinPer100g(),
                savedFood.getFatsPer100g(),
                savedFood.getCarbsPer100g(),
                savedFood.getProteinPer100ml(),
                savedFood.getFatsPer100ml(),
                savedFood.getCarbsPer100ml()
        );
    }

    public Food convertToFoodItem(FoodRequest request){
        Food newFood=new Food();
        newFood.setName(request.name());
        newFood.setFoodType(FoodType.valueOf(request.foodType().toUpperCase()));
        newFood.setEnergyPer100g(request.energyPer100g());
        newFood.setEnergyPer100ml(request.energyPer100ml());
        newFood.setProteinPer100g(request.proteinPer100g());
        newFood.setFatsPer100g(request.fatsPer100g());
        newFood.setCarbsPer100g(request.carbsPer100g());
        newFood.setProteinPer100ml(request.proteinPer100ml());
        newFood.setFatsPer100ml(request.fatsPer100ml());
        newFood.setCarbsPer100ml(request.carbsPer100ml());
        return newFood;
    }

    public Optional<FoodResponse> registerFood(FoodRequest request){
        Food newFood=convertToFoodItem(request);
        foodRepo.save(newFood);
        return Optional.of(convertToFoodItemResponse(newFood));
    }

    // update the values
    // either for the admin
    public Optional<FoodResponse> updateFood(Long foodId, FoodUpdateRequest updateRequest){
        Optional<Food> foundFood= getFoodDetails(foodId);
        if(foundFood.isEmpty())return Optional.empty();
        Food getFood=foundFood.get();
        if(getFood.getFoodType().toString().equalsIgnoreCase("liquid")){
            // update the liquid metrics
            getFood.setEnergyPer100ml(updateRequest.energyPer100ml());
            getFood.setProteinPer100ml(updateRequest.proteinPer100ml());
            getFood.setCarbsPer100ml(updateRequest.carbsPer100ml());
            getFood.setFatsPer100ml(updateRequest.fatsPer100ml());
        }
        else{
            getFood.setEnergyPer100g(updateRequest.energyPer100g());
            getFood.setProteinPer100g(updateRequest.proteinPer100g());
            getFood.setCarbsPer100g(updateRequest.carbsPer100g());
            getFood.setFatsPer100g(updateRequest.fatsPer100g());
        }
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
