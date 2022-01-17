package com.kegarlv.camera2xx.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.kegarlv.camera2xx.NativeLib;

import com.kegarlv.camera2xx.ICamera2Api;
import com.kegarlv.camera2xx.IGetCameraIdListCallback;

public class Camera2Service extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new ICamera2Api.Stub() {
            @Override
            public void getCameraIdList(IGetCameraIdListCallback cb) throws RemoteException {
                new NativeLib().getCameraIdList(new NativeLib.GetCameraIdListCallback() {
                    @Override
                    public void cameraIdListReady(String[] ids) {
                        try {
                            cb.cameraIdListReady(ids);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        };
    }
}