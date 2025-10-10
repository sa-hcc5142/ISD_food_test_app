package com.example.food_app_test.Helper;

import android.util.Log;

public class DeliveryCalculator {
    
    private static final String TAG = "DeliveryCalculator";
    
    // Delivery rate: 3 taka per meter from KUET
    private static final double RATE_PER_METER = 0.05;
    
    // Minimum delivery charge (even if very close)
    private static final double MINIMUM_DELIVERY_CHARGE = 60.0;
    
    // Maximum delivery charge (cap for very far distances)
    private static final double MAXIMUM_DELIVERY_CHARGE = 500.0;
    
    /**
     * Calculates delivery charge based on distance from KUET
     * @param distanceInMeters Distance from KUET in meters
     * @return Delivery charge in taka
     */
    public static double calculateDeliveryCharge(double distanceInMeters) {
        Log.d(TAG, "Calculating delivery charge for distance: " + distanceInMeters + " meters");
        
        // Calculate base charge
        double baseCharge = distanceInMeters * RATE_PER_METER;
        
        // Apply minimum charge (can be disabled during testing)
        if (!isIgnoreMinimumForTest() && baseCharge < MINIMUM_DELIVERY_CHARGE) {
            Log.d(TAG, "Applied minimum delivery charge: " + MINIMUM_DELIVERY_CHARGE);
            return MINIMUM_DELIVERY_CHARGE;
        }
        
        // Apply maximum charge cap
        if (baseCharge > MAXIMUM_DELIVERY_CHARGE) {
            Log.d(TAG, "Applied maximum delivery charge cap: " + MAXIMUM_DELIVERY_CHARGE);
            return MAXIMUM_DELIVERY_CHARGE;
        }
        
        Log.d(TAG, "Calculated delivery charge: " + baseCharge + " taka");
        return Math.round(baseCharge * 100.0) / 100.0; // Round to 2 decimal places
    }

    // --- Test helpers -------------------------------------------------
    // When true, the minimum delivery charge is ignored. Use only for testing.
    private static boolean IGNORE_MINIMUM_FOR_TEST = false;

    /**
     * Enable or disable ignoring the minimum delivery charge (testing only).
     * @param ignore true to ignore minimum charge, false to restore normal behavior
     */
    public static void setIgnoreMinimumForTest(boolean ignore) {
        IGNORE_MINIMUM_FOR_TEST = ignore;
        Log.d(TAG, "IGNORE_MINIMUM_FOR_TEST set to: " + IGNORE_MINIMUM_FOR_TEST);
    }

    /**
     * Returns whether the minimum delivery charge is being ignored for tests
     */
    public static boolean isIgnoreMinimumForTest() {
        return IGNORE_MINIMUM_FOR_TEST;
    }
    
    /**
     * Formats delivery information for display
     * @param distanceInMeters Distance in meters
     * @param deliveryCharge Delivery charge in taka
     * @return Formatted string for UI display
     */
    public static String formatDeliveryInfo(double distanceInMeters, double deliveryCharge) {
        double distanceInKm = distanceInMeters / 1000.0;
        return String.format("Distance from KUET: %.2f km | Delivery: ৳%.2f", distanceInKm, deliveryCharge);
    }
    
    /**
     * Formats just the delivery charge for display
     * @param deliveryCharge Delivery charge in taka
     * @return Formatted delivery charge string
     */
    public static String formatDeliveryCharge(double deliveryCharge) {
        return String.format("৳%.2f", deliveryCharge);
    }
    
    /**
     * Formats distance for display
     * @param distanceInMeters Distance in meters
     * @return Formatted distance string
     */
    public static String formatDistance(double distanceInMeters) {
        if (distanceInMeters < 1000) {
            return String.format("%.0f m from KUET", distanceInMeters);
        } else {
            double distanceInKm = distanceInMeters / 1000.0;
            return String.format("%.2f km from KUET", distanceInKm);
        }
    }
    
    /**
     * Gets the current delivery rate per meter
     * @return Rate per meter in taka
     */
    public static double getDeliveryRate() {
        return RATE_PER_METER;
    }
    
    /**
     * Gets minimum delivery charge
     * @return Minimum delivery charge in taka
     */
    public static double getMinimumDeliveryCharge() {
        return MINIMUM_DELIVERY_CHARGE;
    }
    
    /**
     * Gets maximum delivery charge
     * @return Maximum delivery charge in taka
     */
    public static double getMaximumDeliveryCharge() {
        return MAXIMUM_DELIVERY_CHARGE;
    }
    
    /**
     * Calculates total order amount (food + delivery)
     * @param foodTotal Total cost of food items
     * @param deliveryCharge Delivery charge
     * @return Total order amount
     */
    public static double calculateTotalOrderAmount(double foodTotal, double deliveryCharge) {
        double total = foodTotal + deliveryCharge;
        Log.d(TAG, "Total order: Food ৳" + foodTotal + " + Delivery ৳" + deliveryCharge + " = ৳" + total);
        return Math.round(total * 100.0) / 100.0; // Round to 2 decimal places
    }
}