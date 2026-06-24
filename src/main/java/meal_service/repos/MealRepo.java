package meal_service.repos;

import meal_service.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepo extends JpaRepository<Meal,Long> {
    List<Meal> findByUserId(Long userId);
}
