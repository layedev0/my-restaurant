package heritage.africca.accel_connect.repository;

import heritage.africca.accel_connect.DTO.MealDTO;
import heritage.africca.accel_connect.DTO.RestaurantDTO;
import heritage.africca.accel_connect.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT new heritage.africca.accel_connect.DTO.MealDTO(p.id, p.mealName, p.unitPrice, p.description) FROM Meal p WHERE p.id = :id")
    Optional<MealDTO> findMealByID(@Param("id") Long id);

    @Query("SELECT new heritage.africca.accel_connect.DTO.MealDTO(id, mealName, unitPrice, description) FROM Meal ")
    List<MealDTO> findAllMeal();
}
