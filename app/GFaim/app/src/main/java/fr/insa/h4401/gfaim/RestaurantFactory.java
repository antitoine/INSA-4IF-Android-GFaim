package fr.insa.h4401.gfaim;

import java.util.HashMap;

public abstract class RestaurantFactory {

    public enum name { SNACK_CAMPUS, GRAND_RU, BEURK, GRILLON, OLIVIER };

    private static HashMap<name, Restaurant> restaurantsList = new HashMap<>();

    public static Restaurant getRestaurant(name name){
        if(restaurantsList.size() == 0) {
            restaurantsList.put(RestaurantFactory.name.SNACK_CAMPUS, new Restaurant(
                    45.777154, 4.874535, "Snack du campus", 4, 3, "Ouvert", "Prix correct", 7, 7
            ));

            restaurantsList.put(RestaurantFactory.name.GRAND_RU,  new Restaurant(
                    45.780901, 4.876403, "Restau U", 2, 10, "Ouvert", "Prix bas", 12, 5
            ));
        }

        return restaurantsList.get(name);
    }


}
