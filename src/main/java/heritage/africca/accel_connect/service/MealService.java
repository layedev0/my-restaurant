package heritage.africca.accel_connect.service;

import heritage.africca.accel_connect.DTO.MealDTO;
import heritage.africca.accel_connect.entity.Meal;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface MealService {


    List<MealDTO> list();
    MealDTO get(Long id) throws ChangeSetPersister.NotFoundException;
    Meal create(MealDTO mealDTO);
    Meal update(Long id, Long restaurantId, Meal meal) throws ChangeSetPersister.NotFoundException;
    void delete(Long id);
}
