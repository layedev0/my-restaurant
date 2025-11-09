package heritage.africca.accel_connect.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {

    private Long id;


    private String mealName;


    private double unitPrice;

    private String description;
}
