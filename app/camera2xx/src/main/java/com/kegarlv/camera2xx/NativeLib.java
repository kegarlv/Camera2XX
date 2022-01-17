package com.kegarlv.camera2xx;

public class NativeLib {

    // Used to load the 'camera2xx' library on application startup.
    static {
        System.loadLibrary("camera2xx");
    }

    public interface GetCameraIdListCallback {
        void cameraIdListReady(String[] ids);
    }
    public native void getCameraIdList(GetCameraIdListCallback callback);
}