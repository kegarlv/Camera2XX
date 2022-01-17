package com.kegarlv.camera2xx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kegarlv.camera2xx.ICamera2Api;
import com.kegarlv.camera2xx.IGetCameraIdListCallback;
import com.kegarlv.camera2xx.R;
import com.kegarlv.camera2xx.services.Camera2Service;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ICamera2Api mCameraService = null;
    private TextView mTextView = null;
    private final IGetCameraIdListCallback mCameraListReady = new IGetCameraIdListCallback.Stub() {
        @Override
        public void cameraIdListReady(String[] ids) throws RemoteException {
            Log.d(TAG, String.format("cameraIdListReady: got %1d cameras", ids.length));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), String.format("Found %1d cameras", ids.length), Toast.LENGTH_SHORT).show();
                    mTextView.setText(String.join(",", ids));
                }
            });
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            mCameraService = ICamera2Api.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            mCameraService = null;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //auto-create & bind service
        Intent intent = new Intent(MainActivity.this, Camera2Service.class);
        intent.setAction(ICamera2Api.class.getName());
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);


        // setup onClickListener for querying service
        findViewById(R.id.getCameraIdsBtn).setOnClickListener(view -> {
            mTextView.setText("");
            if(mCameraService == null) {
                Log.e(TAG, "getCameraIdsBtn: Service is not available right now!");
                return;
            }

            try {
                mCameraService.getCameraIdList(mCameraListReady);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "getCameraIdsBtn: Call to service failed!");
            }
        });

        mTextView = findViewById(R.id.textView);
    }
}