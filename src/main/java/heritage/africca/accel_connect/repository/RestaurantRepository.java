package heritage.africca.accel_connect.repository;

import heritage.africca.accel_connect.DTO.RestaurantDTO;
import heritage.africca.accel_connect.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT new heritage.africca.accel_connect.DTO.RestaurantDTO(p.id, p.restaurantName, p.address, p.contact) FROM Restaurant p WHERE p.id = :id")
    Optional<RestaurantDTO> findByID(@Param("id") Long id);

    @Query("SELECT new heritage.africca.accel_connect.DTO.RestaurantDTO(id, restaurantName, address, contact) FROM Restaurant")
    List<RestaurantDTO> findAllRestaurant();

}
