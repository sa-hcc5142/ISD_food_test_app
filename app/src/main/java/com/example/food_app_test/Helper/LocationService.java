package com.example.food_app_test.Helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

public class LocationService {
    
    private static final String TAG = "LocationService";
    
    // KUET (Khulna University of Engineering & Technology) coordinates
    private static final double KUET_LATITUDE = 22.9035;
    private static final double KUET_LONGITUDE = 89.5128;
    
    private FusedLocationProviderClient fusedLocationClient;
    private Context context;
    
    public interface LocationCallback {
        void onLocationReceived(double latitude, double longitude, double distanceFromKUET);
        void onLocationError(String error);
    }
    
    public LocationService(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }
    
    /**
     * Gets user's current location and calculates distance from KUET
     * (Method name for compatibility with CartActivity)
     */
    public void getCurrentLocation(LocationCallback callback) {
        getCurrentLocationAndCalculateDistance(context, callback);
    }
    
    /**
     * Gets user's current location and calculates distance from KUET
     */
    public void getCurrentLocationAndCalculateDistance(Context context, LocationCallback callback) {
        Log.d(TAG, "Getting current location...");
        
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && 
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location permissions not granted");
            callback.onLocationError("Location permissions not granted. Please enable location access in settings.");
            return;
        }
        
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double userLat = location.getLatitude();
                        double userLng = location.getLongitude();

                        Log.d(TAG, "User location: " + userLat + ", " + userLng);
                        Log.d(TAG, "KUET location: " + KUET_LATITUDE + ", " + KUET_LONGITUDE);

                        // Calculate distance from KUET
                        double distanceInMeters = calculateDistanceFromKuet(userLat, userLng);

                        Log.d(TAG, "Distance from KUET: " + distanceInMeters + " meters");

                        callback.onLocationReceived(userLat, userLng, distanceInMeters);
                    } else {
                        Log.w(TAG, "Last location is null — trying getCurrentLocation() as fallback");

                        // Try a fresh current location request (may prompt the system to fetch a location)
                        CancellationTokenSource cts = new CancellationTokenSource();
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.getToken())
                                .addOnSuccessListener(loc -> {
                                    if (loc != null) {
                                        double userLat = loc.getLatitude();
                                        double userLng = loc.getLongitude();

                                        Log.d(TAG, "User location (current): " + userLat + ", " + userLng);

                                        double distanceInMeters = calculateDistanceFromKuet(userLat, userLng);
                                        Log.d(TAG, "Distance from KUET (current): " + distanceInMeters + " meters");

                                        callback.onLocationReceived(userLat, userLng, distanceInMeters);
                                    } else {
                                        Log.e(TAG, "getCurrentLocation returned null");
                                        callback.onLocationError("Unable to get current location. Please ensure GPS or emulator location is enabled.");
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "getCurrentLocation failed: " + e.getMessage());
                                    callback.onLocationError("Failed to get location: " + e.getMessage());
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to get last location: " + e.getMessage());
                    callback.onLocationError("Failed to get location: " + e.getMessage());
                });
    }
    
    /**
     * Calculates distance between user location and KUET using Haversine formula
     * @param userLat User's latitude
     * @param userLng User's longitude
     * @return Distance in meters
     */
    public double calculateDistanceFromKuet(double userLat, double userLng) {
        return calculateDistance(userLat, userLng, KUET_LATITUDE, KUET_LONGITUDE);
    }
    
    /**
     * Calculates distance between two points using Haversine formula
     * @param lat1 First point latitude
     * @param lng1 First point longitude
     * @param lat2 Second point latitude
     * @param lng2 Second point longitude
     * @return Distance in meters
     */
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS = 6371000; // Earth's radius in meters
        
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLatRad = Math.toRadians(lat2 - lat1);
        double deltaLngRad = Math.toRadians(lng2 - lng1);
        
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(deltaLngRad / 2) * Math.sin(deltaLngRad / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        double distance = EARTH_RADIUS * c;
        
        Log.d(TAG, "Calculated distance: " + distance + " meters");
        return distance;
    }
    
    /**
     * Gets KUET coordinates for reference
     */
    public static double[] getKuetCoordinates() {
        return new double[]{KUET_LATITUDE, KUET_LONGITUDE};
    }
    
    /**
     * Stops location updates (cleanup method)
     */
    public void stopLocationUpdates() {
        Log.d(TAG, "Stopping location updates");
        // For single location requests, no ongoing updates to stop
        // This method is for compatibility with CartActivity lifecycle
    }
}