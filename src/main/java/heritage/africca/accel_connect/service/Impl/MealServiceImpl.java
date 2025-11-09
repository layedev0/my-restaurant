package heritage.africca.accel_connect.service.Impl;

import heritage.africca.accel_connect.DTO.MealDTO;
import heritage.africca.accel_connect.entity.Meal;
import heritage.africca.accel_connect.entity.Restaurant;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.repository.RestaurantRepository;
import heritage.africca.accel_connect.service.MealService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MealServiceImpl implements MealService {

private final MealRepository mealRepository;
private final RestaurantRepository restaurantRepository;

    public MealServiceImpl(MealRepository mealRepository, RestaurantRepository restaurantRepository) {
        this.mealRepository = mealRepository;

        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public List<MealDTO> list() {

        return mealRepository.findAllMeal();
    }

    @Transactional(readOnly = true)
    public MealDTO get(Long id) throws ChangeSetPersister.NotFoundException {
        return mealRepository.findMealByID(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found"));
    }


    public Meal create(MealDTO mealDTO) {

        Meal meal = new Meal();
        meal.setMealName(mealDTO.getMealName());
        meal.setUnitPrice(mealDTO.getUnitPrice());
        meal.setDescription(mealDTO.getDescription());

        return mealRepository.save(meal);
    }

    public Meal update(Long id, Long restaurantId, Meal meal) throws ChangeSetPersister.NotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        //meal.set
        return mealRepository.save(meal);
    }

    public void delete(Long id) {
        mealRepository.deleteById(id);
    }
}
