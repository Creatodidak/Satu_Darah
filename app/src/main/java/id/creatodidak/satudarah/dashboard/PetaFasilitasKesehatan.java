package id.creatodidak.satudarah.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.ListfaskesItem;
import id.creatodidak.satudarah.plugin.BitmapUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetaFasilitasKesehatan extends AppCompatActivity implements OnMapReadyCallback {
    Endpoint endpoint;
    MapView mMap;
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta_fasilitas_kesehatan);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        mMap = findViewById(R.id.mapView);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);
        
        loadMarker();
    }

    private void loadMarker() {
        Call<List<ListfaskesItem>> call = endpoint.allfaskes();
        call.enqueue(new Callback<List<ListfaskesItem>>() {
            @Override
            public void onResponse(Call<List<ListfaskesItem>> call, Response<List<ListfaskesItem>> response) {
                if(response.body() != null && response.isSuccessful()){
                    for(ListfaskesItem item : response.body()){
                        Bitmap customMarkerBitmap = BitmapUtils.getBitmapFromVectorDrawable(PetaFasilitasKesehatan.this, R.drawable.rs);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(customMarkerBitmap, 70, 70, false);
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())))
                                .title(item.getNama())
                                .anchor(0.5f, 0.5f)
                                .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                        map.addMarker(markerOptions);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListfaskesItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setMinZoomPreference(7);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0.5149987997797213, 109.76923270853995), 9));
    }

    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }
}