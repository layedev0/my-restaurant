package heritage.africca.accel_connect.entity;

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
public class Cart implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> items = new ArrayList<>();

    private Double totalPrice = 0.0;

    public void addItem(CartItem item) {
        this.items.add(item);
        this.totalPrice += item.getMeal().getUnitPrice() * item.getQuantity();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        this.totalPrice -= item.getMeal().getUnitPrice() * item.getQuantity();
    }
}
