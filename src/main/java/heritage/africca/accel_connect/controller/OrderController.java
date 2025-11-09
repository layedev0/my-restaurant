package heritage.africca.accel_connect.controller;


import heritage.africca.accel_connect.entity.Order;
import heritage.africca.accel_connect.service.OrderService;
import heritage.africca.accel_connect.service.CustomerService;
import heritage.africca.accel_connect.service.MealService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService OrderService;

    private final CustomerService customerService;


    private final MealService mealService;

    public OrderController(OrderService OrderService, CustomerService customerService, MealService mealService) {
        this.OrderService = OrderService;
        this.customerService = customerService;
        this.mealService = mealService;
    }

    @GetMapping(produces="application/json")
    public List<Order> all(){
        return OrderService.list();
    }
    @GetMapping("/{id}") public Order one(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return OrderService.get(id);
    }

    @PostMapping
    public Order create(@RequestBody Order order, @RequestParam Long customerId) throws ChangeSetPersister.NotFoundException {

        return  OrderService.create(order, customerId);
    }

//    @PutMapping("/{id}")
//    public Order update(@PathVariable Long id, @RequestParam Long customerId, @RequestParam Long platId, @RequestParam String date) throws ChangeSetPersister.NotFoundException {
//
//        return OrderService.update(id, customerId, platId, LocalDateTime.parse(date));
//    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){
        OrderService.delete(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> listByCustomer(@PathVariable Long customerId) throws ChangeSetPersister.NotFoundException {
        return OrderService.listByCustomer(customerId);
    }

    @GetMapping("/customer/{customerId}/total-items")
    public Integer getTotalItemsOrderedByCustomer(@PathVariable Long customerId) throws ChangeSetPersister.NotFoundException {
        return OrderService.getTotalItemsOrderedByCustomer(customerId);
    }

}
