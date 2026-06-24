package meal_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.node.DoubleNode;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
//@Embeddable
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodId;
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    private String name;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private Double energy;
    private Double protein;
    private Double fats;
    private Double carbs;
}
