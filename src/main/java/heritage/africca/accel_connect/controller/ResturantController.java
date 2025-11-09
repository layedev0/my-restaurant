package heritage.africca.accel_connect.controller;


import heritage.africca.accel_connect.DTO.MealDTO;
import heritage.africca.accel_connect.DTO.RestaurantDTO;
import heritage.africca.accel_connect.entity.Meal;
import heritage.africca.accel_connect.entity.Restaurant;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.repository.RestaurantRepository;
import heritage.africca.accel_connect.service.MealService;
import heritage.africca.accel_connect.service.RestaurantService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants")
@CrossOrigin("*")
public class ResturantController {


    private final RestaurantService restaurantService;
    private final MealService mealService;
    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    public ResturantController(RestaurantService restaurantService, MealService mealService, RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

//    @GetMapping("/all")
//    public List<Restaurant> all() {
//
//        return restaurantService.list();
//    }

    // liste de tous les restaurants
    @GetMapping("/all")
    public List<RestaurantDTO> listAllRestaurant() {

        return restaurantService.displayAllRestaurants();
    }
    @GetMapping("/{id}")
    public Restaurant one(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {

        return restaurantService.getId(id);
    }

    // create un nouveau restaurant
    @PostMapping("api/v1/restaurants")
    @PreAuthorize("hasRole('role_admin')")
    public Restaurant create(@RequestBody RestaurantDTO restaurantDTO) {

        return restaurantService.create(restaurantDTO);

    }
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        restaurant.setId(id);
        return restaurantService.update(id,restaurant);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        restaurantService.delete(id);
    }


    // Ajouter un plat à un restaurant
    @PostMapping("/{id}/meals/{mealId}")
    @Transactional
    public Restaurant addPlat(@PathVariable Long id, @PathVariable Long mealId)  {

      Restaurant restaurant =  restaurantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
      Meal meal =  mealRepository.findById(mealId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found"));

      // Ensure both entities have IDs
      if (restaurant.getId() == null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID is null");
      }
      if (meal.getId() == null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Meal ID is null");
      }
      
      // Check if meal is already associated with restaurant to avoid duplicates
      boolean alreadyExists = restaurant.getMeals().stream()
              .anyMatch(m -> m.getId() != null && m.getId().equals(meal.getId()));
      
      if (!alreadyExists) {
          // Add meal to restaurant (owner side of relationship)
          restaurant.getMeals().add(meal);
          // Update the inverse side of the bidirectional relationship
          if (meal.getRestaurants().stream().noneMatch(r -> r.getId() != null && r.getId().equals(restaurant.getId()))) {
              meal.getRestaurants().add(restaurant);
          }
      }
      
      return restaurantRepository.save(restaurant);
    }




}
