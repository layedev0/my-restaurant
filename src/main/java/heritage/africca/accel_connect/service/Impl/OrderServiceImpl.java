package heritage.africca.accel_connect.service.Impl;

import heritage.africca.accel_connect.entity.Order;
import heritage.africca.accel_connect.entity.Customer;
import heritage.africca.accel_connect.entity.Meal;
import heritage.africca.accel_connect.entity.CartItem;
import heritage.africca.accel_connect.repository.OrderRepository;
import heritage.africca.accel_connect.repository.CustomerRepository;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.service.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {




    private final OrderRepository OrderRepository;
    private final CustomerRepository customerRepo;
    private final MealRepository mealRepository;

    public OrderServiceImpl(OrderRepository OrderRepository, CustomerRepository customerRepo, MealRepository mealRepository) {
        this.OrderRepository = OrderRepository; this.customerRepo = customerRepo; this.mealRepository = mealRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> list() { return OrderRepository.findAll(); }

    @Transactional(readOnly = true)
    public Order get(Long id) throws ChangeSetPersister.NotFoundException {
        return OrderRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Order create(Order order, Long customerId) throws ChangeSetPersister.NotFoundException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        order.setCustomer(customer);
        return OrderRepository.save(order);
    }

//    public Order update(Long id, Long customerId, Long platId, LocalDateTime date) throws ChangeSetPersister.NotFoundException {
//        Customer customer = customerRepo.findById(customerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
//        Meal meal = mealRepository.findById(platId).orElseThrow(ChangeSetPersister.NotFoundException::new);
//        //Order cmd = new Order(id, c, p, date);
//        Order Order = new Order();
//        Order.setMeal(meal);
//        return OrderRepository.save(Order);
//    }

    public void delete(Long id) {
        OrderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> listByCustomer(Long customerId) throws ChangeSetPersister.NotFoundException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return OrderRepository.findByCustomer(customer);
    }

    @Transactional(readOnly = true)
    public Integer getTotalItemsOrderedByCustomer(Long customerId) throws ChangeSetPersister.NotFoundException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Order> orders = OrderRepository.findByCustomer(customer);
        LocalDate today = LocalDate.now();
        
        return orders.stream()
                .filter(order -> order.getDate() != null && order.getDate().toLocalDate().equals(today))
                .filter(order -> order.getOrderItems() != null)
                .flatMap(order -> order.getOrderItems().stream())
                .filter(item -> item.getQuantity() != null)
                .mapToInt(CartItem::getQuantity)
                .sum();
    }



}
