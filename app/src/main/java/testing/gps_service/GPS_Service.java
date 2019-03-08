package testing.gps_service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by filipp on 6/16/2016.
 */
public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    /*Intent i = new Intent("location_update");
                    i.putExtra("laa",location.getLatitude());
                    i.putExtra("loo",location.getLongitude());
                    sendBroadcast(i);*/

                    //double latit = (double) intent.getExtras().get("laa");
                    //double longit = (double) intent.getExtras().get("loo");

                    double latit = location.getLatitude();
                    double longit = location.getLongitude();

                    final String lat = String.valueOf(latit);
                    final String lng = String.valueOf(longit);

                    class AddCoord extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            //loading = ProgressDialog.show(GPS_Service.this, "Adding...", "Wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            //loading.dismiss();
                            //Toast.makeText(GPS_Service.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Config.KEY_EMP_LAT, lat);
                            params.put(Config.KEY_EMP_LNG, lng);
                            //params.put(Config.KEY_EMP_SAL, sal);

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Config.URL_ADD, params);
                            return res;
                        }
                    }

                    AddCoord ac = new AddCoord();
                    ac.execute();




                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,0,listener);

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);

        }
    }
}
