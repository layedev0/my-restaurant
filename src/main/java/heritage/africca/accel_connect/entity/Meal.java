package heritage.africca.accel_connect.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meal implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealName;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "meals")
    @JsonIgnoreProperties("meals")
    private List<Restaurant> restaurants = new ArrayList<>();

    @ManyToMany(mappedBy = "meals")
    @JsonIgnoreProperties("menus")
    private List<Menu> menus = new ArrayList<>();
}
