package meal_service.repos;

import meal_service.models.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealItemRepo extends JpaRepository<MealItem,Long> {
    List<MealItem> findAllBYMeal_MealId(Long mealId);
}
