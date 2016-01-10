package fr.insa.h4401.gfaim;

import android.widget.ArrayAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class RestaurantFactory {

    public enum name { SNACK_CAMPUS, GRAND_RU, BEURK, GRILLON, OLIVIER };

    private static HashMap<name, Restaurant> restaurantsList = new HashMap<>();

    private static HashMap<Integer, Allergie> allergies = new HashMap<>();

    public static HashMap<Integer, Allergie> getAllergies(){
        if(allergies.isEmpty()){
            allergies.put(0, new Allergie(true, "Gluten"));
            allergies.put(1, new Allergie(false, "Crustacés"));
            allergies.put(2, new Allergie(false, "Mollusques"));
            allergies.put(3, new Allergie(false, "Oeufs"));
            allergies.put(4, new Allergie(false, "Poissons"));
            allergies.put(5, new Allergie(false, "Arachides"));
            allergies.put(6, new Allergie(false, "Soja"));
            allergies.put(7, new Allergie(false, "Lait"));
            allergies.put(8, new Allergie(false, "Fruits à coques"));
            allergies.put(9, new Allergie(false, "Céleri"));
            allergies.put(10, new Allergie(false, "Moutarde"));
            allergies.put(11, new Allergie(false, "Graines de sésame"));
            allergies.put(12, new Allergie(false, "Anhydride sulfureux et sulfites"));
            allergies.put(13, new Allergie(false, "Lupin"));
        }

        return allergies;
    }

    private static HashMap<name, Restaurant> getRestaurantsList() {
        if(restaurantsList.isEmpty()) {
            restaurantsList.put(name.SNACK_CAMPUS, new Restaurant(
                    name.SNACK_CAMPUS.toString(), 45.777154, 4.874535, "Snack du campus", 4, 3, "Ouvert", "5 à 10 €", 7, 7, R.drawable.snack_campus,
                    "Snack / Tacos", true));

            restaurantsList.put(name.GRAND_RU,  new Restaurant(
                    name.GRAND_RU.toString(), 45.780901, 4.876403, "Restau U", 2, 10, "Ouvert", "Moins de 6 €", 12, 5, R.drawable.restau_u,
                    "Restaurant Universitaire", false));

            restaurantsList.put(name.GRILLON,  new Restaurant(
                    name.GRILLON.toString(), 45.78385655, 4.87506643, "Le Grillon", 3, 15, "Ouvert", "Moins de 6 €", 10, 4, R.drawable.grillon,
                    "Self - Grillades", false));
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
