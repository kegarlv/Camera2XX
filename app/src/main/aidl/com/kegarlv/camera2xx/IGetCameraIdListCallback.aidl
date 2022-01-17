// IGetCameraIdListCallback.aidl
package com.kegarlv.camera2xx;

interface IGetCameraIdListCallback {
     oneway void cameraIdListReady(in String[] list);
}