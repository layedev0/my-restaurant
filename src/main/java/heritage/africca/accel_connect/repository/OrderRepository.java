package heritage.africca.accel_connect.repository;

import heritage.africca.accel_connect.entity.Order;
import heritage.africca.accel_connect.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomer(Customer customer);
}
