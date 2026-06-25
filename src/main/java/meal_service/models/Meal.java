package meal_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meal_service.models.enums.MealType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meals")
@Getter
@Setter
@RequiredArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mealId;
    private Long userId;
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    private MealType mealType;
    private Double mealCalorie;
    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MealItem> mealItems;
    private List<Nutrient> mealMacros;


    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}
