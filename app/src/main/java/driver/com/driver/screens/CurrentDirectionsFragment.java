package driver.com.driver.screens;

/**
 * Created by kalaivani on 2/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import driver.com.driver.R;
import okhttp3.Route;

/**
 * Created by kalaivani on 2/23/2016.
 */
public class CurrentDirectionsFragment extends Fragment {

    private GoogleMap googleMap;
    double latitude = 11.93;
    double longitude = 79.83;

    String start, end;
    ImageView iv;
    View rootView;
    Context ctx;
    double latitudes = 9.93;
    double longitudes = 81.83;
    RequestQueue queue;
    SharedPreferences preference;
    String authToken;
    String userId;
    String orderId;

    public CurrentDirectionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        try {
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_directions, container, false);
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
        LoadMap();
    }


    private void LoadMap() {
        try {

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("welcome");
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            MarkerOptions marker1 = new MarkerOptions().position(new LatLng(latitudes, longitudes)).title("welcome");
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(marker1);

            CameraPosition cameraPosition1 = new CameraPosition.Builder().target(new LatLng(latitudes, longitudes)).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);


            Polyline line = googleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(latitude, longitude), new LatLng(latitudes, longitudes))
                    .width(5)
                    .color(Color.RED));
        } catch (Exception e) {


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initilizeMap();
    }

}

