package heritage.africca.accel_connect.service;


import heritage.africca.accel_connect.entity.Order;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderService {


    List<Order> list();
    Order get(Long id) throws ChangeSetPersister.NotFoundException;
    Order create(Order order, Long customerId) throws ChangeSetPersister.NotFoundException;
//    Order update(Long id, Long customerId, Long platId, LocalDateTime date) throws ChangeSetPersister.NotFoundException;
    void delete(Long id);
    List<Order> listByCustomer(Long customerId) throws ChangeSetPersister.NotFoundException;
    Integer getTotalItemsOrderedByCustomer(Long customerId) throws ChangeSetPersister.NotFoundException;





}
