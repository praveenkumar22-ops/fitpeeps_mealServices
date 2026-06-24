package meal_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MealItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mealItemId;

    @ManyToOne
    @JoinColumn( name = "mealId")
    private Meal meal;

    @ManyToOne
    @JoinColumn( name = "foodId")
    private Food food;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Unit foodType;
}
