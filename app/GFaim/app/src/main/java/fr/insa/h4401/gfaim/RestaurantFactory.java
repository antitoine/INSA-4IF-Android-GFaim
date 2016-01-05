package fr.insa.h4401.gfaim;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class RestaurantFactory {

    public enum name { SNACK_CAMPUS, GRAND_RU, BEURK, GRILLON, OLIVIER };

    private static HashMap<name, Restaurant> restaurantsList = new HashMap<>();

    private static HashMap<name, Restaurant> getRestaurantsList() {
        if(restaurantsList.isEmpty()) {
            restaurantsList.put(name.SNACK_CAMPUS, new Restaurant(
                    name.SNACK_CAMPUS.toString(), 45.777154, 4.874535, "Snack du campus", 4, 3, "Ouvert", "5 à 10 €", 7, 7, R.drawable.snack_campus
            ));

            restaurantsList.put(name.GRAND_RU,  new Restaurant(
                    name.GRAND_RU.toString(), 45.780901, 4.876403, "Restau U", 2, 10, "Ouvert", "Moins de 6 €", 12, 5, R.drawable.restau_u
            ));

            restaurantsList.put(name.GRILLON,  new Restaurant(
                    name.GRILLON.toString(), 45.78385655, 4.87506643, "Le Grillon", 3, 15, "Ouvert", "Moins de 6 €", 10, 4, R.drawable.grillon
            ));
        }
        return restaurantsList;
    }

    public static Restaurant getRestaurant(name name){
        return getRestaurantsList().get(name);
    }

    public static Collection<Restaurant> getAllRestaurants() {
        return getRestaurantsList().values();
    }

}
