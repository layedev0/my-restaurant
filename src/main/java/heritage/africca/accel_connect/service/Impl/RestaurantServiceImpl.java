package heritage.africca.accel_connect.service.Impl;

import heritage.africca.accel_connect.DTO.RestaurantDTO;
import heritage.africca.accel_connect.entity.Restaurant;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.repository.RestaurantRepository;
import heritage.africca.accel_connect.service.MealService;
import heritage.africca.accel_connect.service.RestaurantService;
import jakarta.validation.constraints.Null;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {


    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final MealService mealService;
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository, MealService mealService) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.mealService = mealService;
    }

    @Transactional(readOnly = true)
    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<RestaurantDTO> displayAllRestaurants() {

        return restaurantRepository.findAllRestaurant();
        //return null;
    }

    @Transactional(readOnly = true)
    public RestaurantDTO getRestautById(Long id) throws ChangeSetPersister.NotFoundException {
        return restaurantRepository.findByID(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found"));
    }

    @Override
    public Restaurant getId(Long id) throws ChangeSetPersister.NotFoundException {
        return restaurantRepository.getReferenceById(id);
    }


    public Restaurant create(RestaurantDTO restaurantDTO) {

        Restaurant restaurant = new Restaurant();
        restaurant.setContact(restaurantDTO.getContact());
        restaurant.setRestaurantName(restaurantDTO.getRestaurant_name());
        restaurant.setAddress(restaurantDTO.getAddress());
        //restaurant.setMeal(mealRepository.getReferenceById(mealId));
        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(Long id, Restaurant restaurant) { restaurant.setId(id);
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

}
