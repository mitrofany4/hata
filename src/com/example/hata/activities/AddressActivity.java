package com.example.hata.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.hata.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Максим on 22.05.13.
 */
public class AddressActivity extends SherlockFragmentActivity {
    static final LatLng UNIVER = new LatLng(48.005199,37.799288);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        setContentView(R.layout.map_layout);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        Marker hata = map.addMarker(new MarkerOptions()
                .title("Суши-бар 'японаХата'")
                .position(UNIVER)
                .snippet("Круто!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.japan_home_icon_48)));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(UNIVER, 15));

        map.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);

   }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
//                Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        return true;
    }

}
