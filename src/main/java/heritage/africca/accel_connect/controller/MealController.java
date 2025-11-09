package heritage.africca.accel_connect.controller;


import heritage.africca.accel_connect.DTO.MealDTO;
import heritage.africca.accel_connect.entity.Meal;
import heritage.africca.accel_connect.entity.Restaurant;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.repository.RestaurantRepository;
import heritage.africca.accel_connect.service.MealService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@CrossOrigin(origins = "http://localhost:4200")

public class MealController {


    private final MealService mealService;


    public MealController(MealService mealService) {

        this.mealService = mealService;
    }



    @GetMapping("/all")
    public List<MealDTO> all(){
        return mealService.list();
    }

    @GetMapping("/{id}") public MealDTO one(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {

        return mealService.get(id);
    }

    // création d'un plat pour un restaurant donné
    @PostMapping
    @PreAuthorize("hasRole('role_admin')")
    public Meal create(@RequestBody MealDTO meal){


        return mealService.create(meal);
    }

    @PutMapping("/{id}")
    public Meal update(@PathVariable Long id, @RequestParam Long restaurantId, @RequestBody Meal meal) throws ChangeSetPersister.NotFoundException {

        return mealService.update(id,restaurantId,meal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        mealService.delete(id);
    }


}
