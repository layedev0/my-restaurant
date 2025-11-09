package heritage.africca.accel_connect.DTO;


import heritage.africca.accel_connect.entity.Meal;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Long id;

    private String restaurant_name;

    private String address;

    private String contact;

}
