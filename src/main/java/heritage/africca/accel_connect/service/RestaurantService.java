package heritage.africca.accel_connect.service;

import heritage.africca.accel_connect.DTO.RestaurantDTO;
import heritage.africca.accel_connect.entity.Restaurant;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> list();
    List<RestaurantDTO> displayAllRestaurants();
    RestaurantDTO getRestautById(Long id) throws ChangeSetPersister.NotFoundException;
    Restaurant getId(Long id) throws ChangeSetPersister.NotFoundException;
    Restaurant create(RestaurantDTO restaurantDTO);
    Restaurant update(Long id, Restaurant restaurant);
    void delete(Long id);
}
