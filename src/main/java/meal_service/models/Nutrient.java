package meal_service.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meal_service.models.enums.Unit;

@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class Nutrient {
    private String name;
    private Double value;
}
