package meal_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meal_service.models.enums.FoodType;
import meal_service.models.enums.Unit;

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
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){this.updatedAt=LocalDateTime.now();}
    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
}
