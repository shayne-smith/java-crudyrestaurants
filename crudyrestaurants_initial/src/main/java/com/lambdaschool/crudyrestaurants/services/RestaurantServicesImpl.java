package com.lambdaschool.crudyrestaurants.services;

import com.lambdaschool.crudyrestaurants.models.Restaurant;
import com.lambdaschool.crudyrestaurants.repositories.RestaurantRepository;
import com.lambdaschool.crudyrestaurants.views.MenuCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "restaurantServices")
public class RestaurantServicesImpl implements RestaurantServices
{
    @Autowired
    RestaurantRepository restrepos;

    @Override
    public List<Restaurant> findAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        restrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Restaurant findRestaurantById(long id) {
        return restrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant " + id + " Not Found!"));
    }

    @Override
    public Restaurant findRestaurantByName(String name) {
        Restaurant restaurant = restrepos.findByName(name);

        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant " + name + " Not Found!");
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> findByLikeName(String subname) {
        List<Restaurant> list = restrepos.findByNameContainingIgnoringCase(subname);
        return list;
    }

    @Override
    public List<MenuCounts> countMenusByRestaurant() {
        List<MenuCounts> list = restrepos.findMenuCounts();
        return list;
    }

    @Override
    public List<Restaurant> findRestaurantByDish(String dish) {
        List<Restaurant> list = restrepos.findByMenus_dishContainingIgnoringCase(dish);
        return list;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restrepos.save(restaurant);
    }
}
