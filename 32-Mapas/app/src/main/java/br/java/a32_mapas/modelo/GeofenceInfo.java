package br.java.a32_mapas.modelo;

import com.google.android.gms.location.Geofence;

public class GeofenceInfo {

    public final String mId;
    public final double mLatitude;
    public final double mLongitude;
    public final float mRadius;
    public long mExpirationDuration;
    public int mTransitionType;


    public GeofenceInfo(String geofenceId, double latitude, double longitude,
                        float radius, long expiration, int transition) {
        this.mId = geofenceId;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = radius;
        this.mExpirationDuration = expiration;
        this.mTransitionType = transition;
    }
    public Geofence getGeofence() {
        return new Geofence.Builder()
                .setRequestId(mId)
                .setTransitionTypes(mTransitionType)
                .setCircularRegion(mLatitude, mLongitude, mRadius)
                .setExpirationDuration(mExpirationDuration)
                .build();
    }
}
