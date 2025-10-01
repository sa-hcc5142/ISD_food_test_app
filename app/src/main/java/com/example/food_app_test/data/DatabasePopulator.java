package com.example.food_app_test.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.food_app_test.data.entities.CategoryEntity;
import com.example.food_app_test.data.entities.FoodEntity;
import com.example.food_app_test.data.entities.LocationEntity;
import com.example.food_app_test.data.entities.PriceEntity;
import com.example.food_app_test.data.entities.TimeEntity;

import java.util.Arrays;
import java.util.List;

public class DatabasePopulator {
    
    private final AppDatabase database;
    
    public DatabasePopulator(Context context) {
        this.database = AppDatabase.getDatabase(context);
    }
    
    public void populateDatabase() {
        Log.e("DB_POP_DEBUG", "=== POPULATE DATABASE CALLED ===");
        new PopulateDbAsync(database).execute();
    }
    
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        
        private final AppDatabase database;
        
        PopulateDbAsync(AppDatabase database) {
            this.database = database;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            
            Log.e("DB_POP_DEBUG", "=== POPULATING DATABASE IN BACKGROUND ===");
            
            // Clear all tables first to ensure clean data
            Log.e("DB_POP_DEBUG", "Clearing all existing data...");
            database.foodDao().deleteAllFoods();
            
            Log.e("DB_POP_DEBUG", "Database cleared, inserting fresh data...");
            
            // Populate Categories (with correct relative asset paths)
            List<CategoryEntity> categories = Arrays.asList(
                new CategoryEntity(0, "images/categories/btn_1.png", "Pizza"),
                new CategoryEntity(1, "images/categories/btn_2.png", "Burger"),
                new CategoryEntity(2, "images/categories/btn_3.png", "Chicken"),
                new CategoryEntity(3, "images/categories/btn_4.png", "Sushi"),
                new CategoryEntity(4, "images/categories/btn_5.png", "Meat"),
                new CategoryEntity(5, "images/categories/btn_6.png", "Hotdog"),
                new CategoryEntity(6, "images/categories/btn_7.png", "Drink"),
                new CategoryEntity(7, "images/categories/btn_8.png", "More")
            );
            database.categoryDao().insertCategories(categories);
            
            // Populate Locations
            List<LocationEntity> locations = Arrays.asList(
                new LocationEntity(0, "LA california"),
                new LocationEntity(1, "NY manhattan")
            );
            database.locationDao().insertLocations(locations);
            
            // Populate Times
            List<TimeEntity> times = Arrays.asList(
                new TimeEntity(0, "0 - 10 min"),
                new TimeEntity(1, "10 - 30 min"),
                new TimeEntity(2, "more than 30 min")
            );
            database.timeDao().insertTimes(times);
            
            // Populate Prices
            List<PriceEntity> prices = Arrays.asList(
                new PriceEntity(0, "1$ - 10$"),
                new PriceEntity(1, "10$ - 30$"),
                new PriceEntity(2, "more than 30$")
            );
            database.priceDao().insertPrices(prices);
            
            // Populate Foods (ALL ENTRIES FIXED: imagePath with underscores to match renamed files)
            List<FoodEntity> foods = Arrays.asList(
                // Pizza Category (0) - 6 items
                new FoodEntity(0, true, 0, "A timeless classic, the Margherita pizza.", "images/foods/Margherita.jpg", 1, 10.99, 1, 4.5, 1, 15, "Margherita"),
                new FoodEntity(1, false, 0, "Enjoy the crispy Margherita Flatbread.", "images/foods/Margherita_Flatbread.jpg", 0, 8.99, 0, 4.3, 1, 12, "Margherita Flatbread"),
                new FoodEntity(2, true, 0, "The Meat Feast pizza is a carnivore's extravaganza.", "images/foods/Meat_Feast_pizza.jpg", 1, 14.99, 1, 4.8, 2, 35, "Meat Feast pizza"),
                new FoodEntity(3, false, 0, "Classic Pepperoni Lovers pizza.", "images/foods/Pepperoni_Lovers.jpg", 1, 12.99, 1, 4.6, 1, 20, "Pepperoni Lovers"),
                new FoodEntity(4, false, 0, "Indulge in Four Cheese Delight.", "images/foods/Four_Cheese_Delight.jpg", 0, 11.99, 1, 4.4, 1, 18, "Four Cheese Delight"),
                new FoodEntity(5, false, 0, "Fresh and healthy Veggie Supreme.", "images/foods/Veggie_Supreme.jpg", 0, 10.99, 0, 4.2, 1, 16, "Veggie Supreme"),
                
                // Burger Category (1) - 8 items
                new FoodEntity(10, true, 1, "For bacon aficionados, the Bacon and Cheese Heaven Burger.", "images/foods/Bacon_and_Cheese_Heaven.jpg", 1, 11.99, 1, 4.3, 1, 17, "Bacon and Cheese Heaven"),
                new FoodEntity(11, true, 1, "Experience a flavor explosion with the BBQ Ranch Delight Burger.", "images/foods/BBQ_Ranch_Delight.jpg", 0, 12.49, 1, 4.7, 1, 23, "BBQ Ranch Delight"),
                new FoodEntity(12, false, 1, "The all-time favorite Classic Beef Burger.", "images/foods/Classic_Beef_Burger.jpg", 1, 9.99, 1, 4.5, 1, 15, "Classic Beef Burger"),
                new FoodEntity(13, false, 1, "Spicy kick with the Spicy Jalapeño Burger.", "images/foods/Spicy_Jalapeno_Burger.jpg", 0, 11.49, 1, 4.4, 1, 18, "Spicy Jalapeño Burger"),
                new FoodEntity(14, false, 1, "Mushroom Swiss Delight for mushroom lovers.", "images/foods/Mushroom_Swiss_Delight.jpg", 1, 10.99, 1, 4.3, 1, 16, "Mushroom Swiss Delight"),
                new FoodEntity(15, false, 1, "Tropical Hawaiian Paradise burger.", "images/foods/Hawaiian_Paradise.jpg", 0, 12.99, 1, 4.6, 1, 20, "Hawaiian Paradise"),
                new FoodEntity(16, false, 1, "Mediterranean Joy with fresh ingredients.", "images/foods/Mediterranean_Joy.jpg", 0, 11.99, 1, 4.4, 1, 19, "Mediterranean Joy"),
                new FoodEntity(17, false, 1, "Fresh and light Chicken Avocado Bliss.", "images/foods/Chicken_Avocado_Bliss.jpg", 0, 13.49, 1, 4.5, 1, 21, "Chicken Avocado Bliss"),
                
                // Chicken Category (2) - 12 items
                new FoodEntity(20, true, 2, "Elevate your taste buds with our Lemon Pepper Chicken.", "images/foods/Lemon_Pepper_Chicken.jpg", 0, 9.49, 0, 4.4, 1, 16, "Lemon Pepper Chicken"),
                new FoodEntity(21, true, 2, "Indulge in the rich and savory goodness of our Garlic Parmesan Chicken.", "images/foods/Garlic_Parmesan_Chicken.jpg", 0, 9.99, 0, 4.5, 1, 19, "Garlic Parmesan Chicken"),
                new FoodEntity(22, false, 2, "Crispy and delicious Original Crispy Chicken.", "images/foods/Original_Crispy_Chicken.jpg", 1, 8.99, 0, 4.3, 1, 14, "Original Crispy Chicken"),
                new FoodEntity(23, false, 2, "Spicy Buffalo Wings for wing lovers.", "images/foods/Spicy_Buffalo_Wings.jpg", 0, 10.99, 0, 4.6, 1, 18, "Spicy Buffalo Wings"),
                new FoodEntity(24, false, 2, "Sweet and savory Teriyaki Chicken Wings.", "images/foods/Teriyaki_Chicken_Wings.jpg", 1, 11.49, 0, 4.5, 1, 20, "Teriyaki Chicken Wings"),
                new FoodEntity(25, false, 2, "Honey Mustard Glazed Tenders for a sweet kick.", "images/foods/Honey_Mustard_Glazed_Tenders.jpg", 0, 9.99, 0, 4.4, 1, 17, "Honey Mustard Glazed Tenders"),
                new FoodEntity(26, false, 2, "Korean Fried Chicken with authentic flavors.", "images/foods/Korean_Fried_Chicken.jpg", 1, 12.99, 1, 4.7, 1, 22, "Korean Fried Chicken"),
                new FoodEntity(27, false, 2, "BBQ Chicken Delight with smoky flavors.", "images/foods/BBQ_Chicken_Delight.jpg", 0, 11.99, 1, 4.5, 1, 21, "BBQ Chicken Delight"),
                new FoodEntity(28, false, 2, "Teriyaki Glazed Chicken Thighs.", "images/foods/Teriyaki_Glazed_Chicken_Thighs.jpg", 1, 13.49, 1, 4.6, 1, 24, "Teriyaki Glazed Chicken Thighs"),
                new FoodEntity(29, false, 2, "Southern-Style Chicken Biscuit comfort food.", "images/foods/Southern-Style_Chicken_Biscuit.jpg", 0, 8.99, 0, 4.3, 1, 15, "Southern-Style Chicken Biscuit"),
                new FoodEntity(30, false, 2, "Spinach and Feta Stuffed Chicken.", "images/foods/Spinach_and_Feta_Stuffed_Chicken.jpg", 1, 14.99, 1, 4.7, 2, 28, "Spinach and Feta Stuffed Chicken"),
                new FoodEntity(31, false, 2, "Tropical Teriyaki Pineapple Pleasure.", "images/foods/Teriyaki_Pineapple_Pleasure.jpg", 0, 12.49, 1, 4.4, 1, 19, "Teriyaki Pineapple Pleasure"),
                
                // Sushi Category (3) - 8 items
                new FoodEntity(40, true, 3, "Dive into the Classic California Roll.", "images/foods/California_Roll.jpg", 0, 9.99, 0, 4.6, 1, 20, "California Roll"),
                new FoodEntity(41, false, 3, "Indulge in artistic presentation with the Dragon Roll.", "images/foods/Dragon_Roll.jpg", 1, 12.99, 1, 4.9, 1, 25, "Dragon Roll"),
                new FoodEntity(42, false, 3, "Spicy Tuna Roll for spice lovers.", "images/foods/Spicy_Tuna_Roll.jpg", 0, 11.99, 0, 4.7, 1, 22, "Spicy Tuna Roll"),
                new FoodEntity(43, false, 3, "Beautiful Rainbow Roll with variety.", "images/foods/Rainbow_Roll.jpg", 1, 14.99, 1, 4.8, 1, 26, "Rainbow Roll"),
                new FoodEntity(44, false, 3, "Fresh Salmon Nigiri.", "images/foods/Salmon_Nigiri.jpg", 0, 8.99, 0, 4.5, 1, 15, "Salmon Nigiri"),
                new FoodEntity(45, false, 3, "Premium Sashimi Platter.", "images/foods/Sashimi_Platter.jpg", 1, 18.99, 1, 4.9, 2, 30, "Sashimi Platter"),
                new FoodEntity(46, false, 3, "Crispy Tempura Shrimp Roll.", "images/foods/Tempura_Shrimp_Roll.jpg", 0, 13.99, 1, 4.6, 1, 24, "Tempura Shrimp Roll"),
                new FoodEntity(47, false, 3, "Fresh Veggie Roll for vegetarians.", "images/foods/Veggie_Roll.jpg", 0, 7.99, 0, 4.3, 1, 18, "Veggie Roll"),
                
                // Meat Category (4) - 8 items
                new FoodEntity(50, false, 4, "Savor the richness of our Grilled Ribeye Steak.", "images/foods/Grilled_Ribeye_Steak.jpg", 0, 34.99, 2, 4.6, 1, 25, "Grilled Ribeye Steak"),
                new FoodEntity(51, true, 4, "Indulge in the elegance of our Pan-Seared Garlic Butter Sirloin.", "images/foods/Pan-Seared_Garlic_Butter_Sirloin.jpg", 1, 26.99, 1, 4.7, 1, 23, "Pan-Seared Garlic Butter Sirloin"),
                new FoodEntity(52, false, 4, "Dive into the smoky goodness of our Smoked BBQ Brisket.", "images/foods/Smoked_BBQ_Brisket.jpg", 0, 32.99, 2, 4.8, 2, 60, "Smoked BBQ Brisket"),
                new FoodEntity(53, false, 4, "Immerse yourself in the bold flavors of our Korean BBQ Short Ribs.", "images/foods/Korean_BBQ_Short_Ribs.jpg", 1, 22.99, 1, 4.4, 2, 40, "Korean BBQ Short Ribs"),
                new FoodEntity(54, false, 4, "Premium Bacon-Wrapped Filet Mignon.", "images/foods/Bacon-Wrapped_Filet_Mignon.jpg", 1, 39.99, 2, 4.9, 2, 35, "Bacon-Wrapped Filet Mignon"),
                new FoodEntity(55, false, 4, "Exotic Spicy Moroccan Lamb Chops.", "images/foods/Spicy_Moroccan_Lamb_Chops.jpg", 0, 28.99, 2, 4.5, 2, 45, "Spicy Moroccan Lamb Chops"),
                new FoodEntity(56, false, 4, "Healthy Beef Stir-Fry with Broccoli.", "images/foods/Beef_Stir-Fry_with_Broccoli.jpg", 0, 16.99, 1, 4.3, 1, 22, "Beef Stir-Fry with Broccoli"),
                new FoodEntity(57, false, 4, "Lean Stuffed Bell Peppers with Ground Turkey.", "images/foods/Stuffed_Bell_Peppers_with_Ground_Turkey.jpg", 0, 14.99, 1, 4.2, 1, 28, "Stuffed Bell Peppers with Ground Turkey"),
                
                // Hotdog Category (5) - 8 items
                new FoodEntity(60, true, 5, "Indulge in comfort with the Chili Cheese Dog.", "images/foods/Chili_Cheese_Dog.jpg", 0, 6.49, 0, 4.5, 1, 12, "Chili Cheese Dog"),
                new FoodEntity(61, false, 5, "Take a bite of Chicago with the Chicago Style Hot Dog.", "images/foods/Chicago_Style_Hot_Dog.jpg", 1, 7.49, 0, 4.3, 1, 15, "Chicago Style Hot Dog"),
                new FoodEntity(62, false, 5, "Classic Beef Hot Dog.", "images/foods/Classic_Beef_Hot_Dog.jpg", 0, 5.99, 0, 4.2, 1, 10, "Classic Beef Hot Dog"),
                new FoodEntity(63, false, 5, "Hawaiian BBQ Dog with tropical flavors.", "images/foods/Hawaiian_BBQ_Dog.jpg", 1, 8.49, 0, 4.4, 1, 14, "Hawaiian BBQ Dog"),
                new FoodEntity(64, false, 5, "Spicy Kimchi Hot Dog.", "images/foods/Kimchi_Hot_Dog.jpg", 0, 7.99, 0, 4.3, 1, 13, "Kimchi Hot Dog"),
                new FoodEntity(65, false, 5, "Artisan Pretzel Bun Dog.", "images/foods/Pretzel_Bun_Dog.jpg", 1, 8.99, 0, 4.5, 1, 16, "Pretzel Bun Dog"),
                new FoodEntity(66, false, 5, "Deli-style Reuben Style Hot Dog.", "images/foods/Reuben_Style_Hot_Dog.jpg", 0, 9.49, 0, 4.4, 1, 17, "Reuben Style Hot Dog"),
                new FoodEntity(67, false, 5, "Healthy Veggie Dog with Sauerkraut.", "images/foods/Veggie_Dog_with_Sauerkraut.jpg", 0, 6.99, 0, 4.1, 1, 12, "Veggie Dog with Sauerkraut"),
                
                // Drinks Category (6) - 8 items
                new FoodEntity(70, false, 6, "Refresh with our Fresh Orange Juice.", "images/foods/Fresh_Orange_Juice.jpg", 0, 3.99, 0, 4.6, 0, 5, "Fresh Orange Juice"),
                new FoodEntity(71, false, 6, "Indulge in the sophisticated Espresso Martini.", "images/foods/Espresso_Martini.jpg", 0, 7.99, 0, 4.9, 0, 10, "Espresso Martini"),
                new FoodEntity(72, false, 6, "Healthy and refreshing Coconut Water.", "images/foods/Coconut_Water.jpg", 0, 2.99, 0, 4.3, 0, 3, "Coconut Water"),
                new FoodEntity(73, false, 6, "Energizing Green Tea Latte.", "images/foods/Green_Tea_Latte.jpg", 0, 4.49, 0, 4.4, 0, 7, "Green Tea Latte"),
                new FoodEntity(74, false, 6, "Sweet Iced Caramel Macchiato.", "images/foods/Iced_Caramel_Macchiato.jpg", 0, 5.49, 0, 4.5, 0, 8, "Iced Caramel Macchiato"),
                new FoodEntity(75, false, 6, "Fruity Berry Blast Smoothie.", "images/foods/Berry_Blast_Smoothie.jpg", 0, 6.99, 0, 4.6, 0, 9, "Berry Blast Smoothie"),
                new FoodEntity(76, false, 6, "Tropical Mango Tango Slush.", "images/foods/Mango_Tango_Slush.jpg", 0, 5.99, 0, 4.5, 0, 8, "Mango Tango Slush"),
                new FoodEntity(77, false, 6, "Refreshing Mint Lemonade.", "images/foods/Mint_Lemonade.jpg", 0, 3.49, 0, 4.3, 0, 6, "Mint Lemonade"),
                
                // More Category (7) - 6 items
                new FoodEntity(80, false, 7, "Indulge in the classic Italian comfort of Pasta Carbonara.", "images/foods/Pasta_Carbonara.jpg", 1, 12.99, 1, 4.7, 1, 22, "Pasta Carbonara"),
                new FoodEntity(81, false, 7, "Indulge in the delightful flavors of Shrimp Scampi.", "images/foods/Shrimp_Scampi.jpg", 1, 16.99, 1, 4.8, 2, 32, "Shrimp Scampi"),
                new FoodEntity(82, false, 7, "Spicy and aromatic Thai Red Curry.", "images/foods/Thai_Red_Curry.jpg", 0, 13.99, 1, 4.6, 1, 25, "Thai Red Curry"),
                new FoodEntity(83, false, 7, "Healthy Vegetarian Pad Thai.", "images/foods/Vegetarian_Pad_Thai.jpg", 0, 11.99, 1, 4.4, 1, 20, "Vegetarian Pad Thai"),
                new FoodEntity(84, false, 7, "Fresh and healthy Quinoa Salad Bowl.", "images/foods/Quinoa_Salad_Bowl.jpg", 0, 9.99, 0, 4.3, 1, 15, "Quinoa Salad Bowl"),
                new FoodEntity(85, false, 7, "Colorful Veggie Extravaganza.", "images/foods/Veggie_Extravaganza.jpg", 0, 10.99, 0, 4.2, 1, 18, "Veggie Extravaganza")
            );
            database.foodDao().insertFoods(foods);
            
            Log.e("DB_POP_DEBUG", "=== DATABASE POPULATION COMPLETE ===");
            Log.e("DB_POP_DEBUG", "Inserted " + foods.size() + " food items");
            
            return null;
        }
    }
}