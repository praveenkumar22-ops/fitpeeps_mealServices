package meal_service.repos;

import meal_service.models.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealItemRepo extends JpaRepository<MealItem,Long> {
}
