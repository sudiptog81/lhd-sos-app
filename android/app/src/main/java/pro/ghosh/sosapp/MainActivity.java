package pro.ghosh.sosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pro.ghosh.sosapp.models.Coordinate;
import pro.ghosh.sosapp.models.RequestObject;
import pro.ghosh.sosapp.models.ResponseObject;
import pro.ghosh.sosapp.repos.SmsRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    private String TAG = "MainActivityTag";
    private int LOCATION_REQUEST_CODE = 100;

    private LocationManager locationManager;
    private Button btnSos;
    private SmsRepository smsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsRepository = new SmsRepository();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    LOCATION_REQUEST_CODE
            );
        }

        btnSos = findViewById(R.id.btn_sos);

        btnSos.setOnClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        RequestObject request = new RequestObject(
                new Coordinate(
                        location.getLatitude(),
                        location.getLongitude()
                ).toString(),
                ""
        );

        smsRepository
                .getSmsService()
                .sendSms(request)
                .enqueue(
                        new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                if (response.body() != null && response.code() == 200) {
                                    Toast.makeText(MainActivity.this, "Location Sent", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Sent " + response.body().getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseObject> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Location Not Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

    @Override
    public void onClick(View v) {
        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location Access Not Permitted", Toast.LENGTH_SHORT).show();
            return;
        }

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
    }
}