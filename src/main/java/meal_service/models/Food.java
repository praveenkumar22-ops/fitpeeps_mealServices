package meal_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodId;
    private Long foodItemId;
    private String name;
    private Double quantity;
    private Enum unit;
    private Double proteinPer100g;
    private Double fatsPer100g;
    private Double carbsPer100g;
}
