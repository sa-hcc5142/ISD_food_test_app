package com.example.food_app_test.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationPermissionHelper {
    
    private static final String TAG = "LocationPermissionHelper";
    
    // Request code for location permission
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    
    // Required permissions for location services
    private static final String[] LOCATION_PERMISSIONS = {
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    };
    
    /**
     * Interface for permission result callbacks
     */
    public interface PermissionCallback {
        void onPermissionGranted();
        void onPermissionDenied();
        void onPermissionPermanentlyDenied();
    }
    
    /**
     * Check if location permissions are granted
     * @param context Application context
     * @return true if both fine and coarse location permissions are granted
     */
    public static boolean hasLocationPermissions(Context context) {
        boolean hasFineLocation = ContextCompat.checkSelfPermission(context, 
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        
        boolean hasCoarseLocation = ContextCompat.checkSelfPermission(context, 
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        
        Log.d(TAG, "Location permissions - Fine: " + hasFineLocation + ", Coarse: " + hasCoarseLocation);
        
        return hasFineLocation && hasCoarseLocation;
    }
    
    /**
     * Check if fine location permission is granted (for precise delivery calculation)
     * @param context Application context
     * @return true if fine location permission is granted
     */
    public static boolean hasFineLocationPermission(Context context) {
        boolean hasFineLocation = ContextCompat.checkSelfPermission(context, 
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        
        Log.d(TAG, "Fine location permission: " + hasFineLocation);
        return hasFineLocation;
    }
    
    /**
     * Request location permissions from user
     * @param activity Activity to request permissions from
     */
    public static void requestLocationPermissions(Activity activity) {
        Log.d(TAG, "Requesting location permissions");
        
        ActivityCompat.requestPermissions(activity, 
            LOCATION_PERMISSIONS, 
            LOCATION_PERMISSION_REQUEST_CODE);
    }
    
    /**
     * Check if we should show permission rationale to user
     * @param activity Activity context
     * @return true if we should show rationale
     */
    public static boolean shouldShowPermissionRationale(Activity activity) {
        boolean shouldShowFine = ActivityCompat.shouldShowRequestPermissionRationale(activity, 
            Manifest.permission.ACCESS_FINE_LOCATION);
        
        boolean shouldShowCoarse = ActivityCompat.shouldShowRequestPermissionRationale(activity, 
            Manifest.permission.ACCESS_COARSE_LOCATION);
        
        Log.d(TAG, "Should show rationale - Fine: " + shouldShowFine + ", Coarse: " + shouldShowCoarse);
        
        return shouldShowFine || shouldShowCoarse;
    }
    
    /**
     * Handle permission request results
     * @param requestCode Request code from permission result
     * @param permissions Array of requested permissions
     * @param grantResults Array of grant results
     * @param callback Callback to handle the result
     */
    public static void handlePermissionResult(int requestCode, String[] permissions, 
            int[] grantResults, Activity activity, PermissionCallback callback) {
        
        Log.d(TAG, "Handling permission result for request code: " + requestCode);
        
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            Log.d(TAG, "Request code doesn't match, ignoring");
            return;
        }
        
        if (grantResults.length == 0) {
            Log.d(TAG, "Permission result is empty");
            if (callback != null) {
                callback.onPermissionDenied();
            }
            return;
        }
        
        // Check if all location permissions are granted
        boolean allPermissionsGranted = true;
        for (int i = 0; i < permissions.length; i++) {
            if (isLocationPermission(permissions[i]) && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }
        
        if (allPermissionsGranted) {
            Log.d(TAG, "All location permissions granted");
            if (callback != null) {
                callback.onPermissionGranted();
            }
        } else {
            // Check if user permanently denied permissions
            if (shouldShowPermissionRationale(activity)) {
                Log.d(TAG, "Location permissions denied, can show rationale");
                if (callback != null) {
                    callback.onPermissionDenied();
                }
            } else {
                Log.d(TAG, "Location permissions permanently denied");
                if (callback != null) {
                    callback.onPermissionPermanentlyDenied();
                }
            }
        }
    }
    
    /**
     * Check if a permission is a location permission
     * @param permission Permission string to check
     * @return true if it's a location permission
     */
    private static boolean isLocationPermission(String permission) {
        return Manifest.permission.ACCESS_FINE_LOCATION.equals(permission) || 
               Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission);
    }
    
    /**
     * Get user-friendly explanation for location permission request
     * @return Explanation string
     */
    public static String getPermissionRationaleMessage() {
        return "This app needs location access to calculate delivery charges based on your distance from KUET university. " +
               "Your location data is only used for delivery calculation and is not stored or shared.";
    }
    
    /**
     * Get message for when permission is permanently denied
     * @return Message string
     */
    public static String getPermissionDeniedMessage() {
        return "Location permission is required to calculate delivery charges. " +
               "Please enable location access in Settings → Apps → Food App → Permissions.";
    }
    
    /**
     * Log current permission status for debugging
     * @param context Application context
     */
    public static void logPermissionStatus(Context context) {
        boolean hasFine = ContextCompat.checkSelfPermission(context, 
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        
        boolean hasCoarse = ContextCompat.checkSelfPermission(context, 
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        
        Log.d(TAG, "=== LOCATION PERMISSION STATUS ===");
        Log.d(TAG, "Fine Location: " + (hasFine ? "GRANTED" : "DENIED"));
        Log.d(TAG, "Coarse Location: " + (hasCoarse ? "GRANTED" : "DENIED"));
        Log.d(TAG, "Both Required: " + (hasFine && hasCoarse ? "✓ READY" : "✗ MISSING"));
        Log.d(TAG, "=================================");
    }
}