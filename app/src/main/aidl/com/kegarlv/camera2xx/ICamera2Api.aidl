// ICamera2Api.aidl
package com.kegarlv.camera2xx;

import com.kegarlv.camera2xx.IGetCameraIdListCallback;

interface ICamera2Api {
    oneway void getCameraIdList(in IGetCameraIdListCallback cb);
}