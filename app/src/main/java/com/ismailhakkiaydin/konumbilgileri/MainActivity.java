package com.ismailhakkiaydin.konumbilgileri;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LocationManager mng;
    private TextView txt1, txt2;
    private GpsReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
init();

    }

    private void init() {
        receiver = new GpsReceiver();
        mng = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mng.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, receiver);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
    }

    public class GpsReceiver implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null){
                double enlem = location.getLatitude();
                double boylam = location.getLongitude();
                double irtia = location.getAltitude();
                float hiz = location.getSpeed();
                txt1.setText(String.format("Enlem : %f - Boylam : %f",enlem,boylam));
                txt2.setText(String.format("Hız : %f m/s - İrtifa: %f mt",hiz,irtia));

            }else {
                Toast.makeText(MainActivity.this, "Konum bilgisi alınmıyor...", Toast.LENGTH_LONG).show();
            }
        }


        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderDisabled(String s) {
            MainActivity.this.setTitle("Konum Kapandı");
        }

        @Override
        public void onProviderEnabled(String s) {
            MainActivity.this.setTitle("Konum Açıldı");
        }
    }



}
