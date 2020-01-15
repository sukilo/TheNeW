package ca.bcit.practice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.LinkedHashSet;
import java.util.Set;

public class MapsActivity2 extends FragmentActivity
        implements OnMapReadyCallback {
    private static final String TAG = MapsActivity2.class.getName();
    private GoogleMap googleMap;
    private static final String PREFS_NAME = "Locations";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        settings = getSharedPreferences(PREFS_NAME, 0);
        download();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void download() {
        Ion.with(this).
                load("http://opendata.newwestcity.ca/downloads/significant-buildings-schools/SIGNIFICANT_BLDG_SCHOOLS.json").
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(final Exception ex,
                                                    final JsonObject result) {
                                downloadSuccess(result);
                            }
                        });
    }

    private void downloadSuccess(final JsonObject jsonObject) {
        final JsonArray features;
        features = jsonObject.getAsJsonArray("features");
        SharedPreferences.Editor editor = settings.edit();
        final Set<String> names;

        names = new LinkedHashSet<>();

        for (final JsonElement element : features) {
            final JsonObject feature;
            final JsonObject properties;
            final String name;
            final String desc;
            final double lat;
            final double lng;

            feature = element.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name = properties.get("BLDGNAM").toString().replaceAll("\"","");
            desc = properties.get("STRNAM").toString().replaceAll("\"","");
            Log.v("1",name);
            lng =  properties.get("X").getAsDouble();
            lat = properties.get("Y").getAsDouble();
            names.add(name);
            editor.putString(name + "-desc",desc);
            editor.putFloat(name + "-lat", (float) lat);
            editor.putFloat(name + "-lng", (float) lng);
        }

        editor.putStringSet("names", names);
        editor.commit();
        displayPoints();
    }

    private void displayPoints() {
        final Set<String> names;
        LatLngBounds.Builder builder;

        names = settings.getStringSet("names", null);
        builder = new LatLngBounds.Builder();

        for (final String name : names) {
            final float lat;
            final float lng;
            final String desc;
            LatLng latLng;
            Marker currLocationMarker;

            lat = settings.getFloat(name + "-lat", 0.0f);
            lng = settings.getFloat(name + "-lng", 0.0f);
            desc = settings.getString(name + "-desc", ""); //idk about this

            latLng = new LatLng(lat, lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(name);
            markerOptions.snippet(desc); //THIS MIGHT NOT BE WORING
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            currLocationMarker = googleMap.addMarker(markerOptions);
            builder.include(currLocationMarker.getPosition());
        }

        // https://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
        LatLngBounds bounds = builder.build();

        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            String markerTitle;
            String markerDesc;
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(MapsActivity2.this, onMarkerClicked2.class);
                markerTitle = marker.getTitle();
                markerDesc = marker.getSnippet(); //this should be right

                i.putExtra("title", markerTitle);
                i.putExtra("desc", markerDesc); //this should be right
                startActivity(i);
                return false;
            }
        });
    }

}
