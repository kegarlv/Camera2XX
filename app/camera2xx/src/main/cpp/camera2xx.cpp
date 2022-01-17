#include <jni.h>
#include <string>

#include <camera/NdkCameraManager.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_kegarlv_camera2xx_NativeLib_getCameraIdList(JNIEnv *env, jobject thiz, jobject callback) {
    std::shared_ptr<ACameraManager> cameraManager(ACameraManager_create(),
                                                  [](ACameraManager *ptr) { ACameraManager_delete(ptr); });

    //NOTE find a way to use smart pointer with cameraIdList
    ACameraIdList *cameraIdList = nullptr;
    ACameraManager_getCameraIdList(cameraManager.get(), &cameraIdList);

    auto result = env->NewObjectArray(cameraIdList->numCameras,env->FindClass("java/lang/String"),env->NewStringUTF(""));
    for(int i=0; i<cameraIdList->numCameras; i++) {
        env->SetObjectArrayElement(result, i, env->NewStringUTF(cameraIdList->cameraIds[i]));
    }
    ACameraManager_deleteCameraIdList(cameraIdList);

    auto theClass = env->GetObjectClass(callback);
    auto theMethod = env->GetMethodID(theClass, "cameraIdListReady", "([Ljava/lang/String;)V");

    env->CallVoidMethod(callback, theMethod, result);
}