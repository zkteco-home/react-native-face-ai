package com.faceAI.demo

import android.app.Activity
import android.content.Intent
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.faceAI.demo.SysCamera.addFace.AddFaceImageActivity
import com.faceAI.demo.SysCamera.verify.FaceVerificationActivity
import androidx.activity.result.ActivityResult
import com.facebook.react.bridge.BaseActivityEventListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.facebook.react.bridge.ActivityEventListener


class FaceAISDKModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext), ActivityEventListener { // ← 添加接口


    private var TAG1 = "FACEAISDK"

 init {
        reactContext.addActivityEventListener(this)
    }
    companion object {
        private const val TAG = "FaceAISDKModule"
        var faceCameraViewManager: FaceCameraViewManager? = null
    }



    private val reactContext: ReactApplicationContext = reactContext
    // 用于临时存储 Promise（等待 Activity 返回结果）
    private var activityResultPromise: Promise? = null

    override fun getName(): String = "FaceAISDK"

    // 初始化 SDK
    @ReactMethod
    fun initializeSDK(config: ReadableMap, promise: Promise) {
        try {
            val apiKey = config.getString("apiKey")
            Log.d("FaceRecognition", "Initializing with key: $apiKey")
            FaceAIConfig.init(reactContext.applicationContext)
            promise.resolve("SDK Initialized")
        } catch (e: Exception) {
            promise.reject("INIT_ERROR", e.message)
        }
    }

    // 检测人脸
    @ReactMethod
    fun detectFace(imagePath: String, promise: Promise) {
        try {
            val result = Arguments.createMap().apply {
                putString("faceId", "fake-face-id-123")
                putDouble("confidence", 0.95)
                putString("image", "fake-base64-image")
            }
            promise.resolve(result)
        } catch (e: Exception) {
            promise.reject("DETECT_ERROR", e.message)
        }
    }

    // 添加人脸
    @ReactMethod
    fun addFace(imagePath: String, promise: Promise) {
        try {
            val intent = Intent(reactContext, AddFaceImageActivity::class.java).apply {
                putExtra(AddFaceImageActivity.ADD_FACE_IMAGE_TYPE_KEY, 
                         AddFaceImageActivity.AddFaceImageTypeEnum.FACE_VERIFY.name)
            }
            currentActivity?.startActivity(intent)
            
            val result = Arguments.createMap().apply {
                putString("faceId", "fake-face-id-123")
                putDouble("confidence", 0.95)
                putString("image", "fake-base64-image")
            }
            promise.resolve(result)
        } catch (e: Exception) {
            promise.reject("DETECT_ERROR", e.message)
        }
    }

    // 启动活体登记
    @ReactMethod
    fun startEnroll() {
        Log.d(TAG1, "currentActivity: ")

        val activity = currentActivity ?: run {
          //  promise.reject("ACTIVITY_NULL", "当前没有活跃的 Activity")
            return
        }
        //activityResultPromise = promise
        val intent = Intent(reactContext, AddFaceImageActivity::class.java)
        activity.startActivityForResult(intent, 1001)

    }

    // 启动活体比对
    @ReactMethod
    fun startVerify(face_data:String) {

        val activity = currentActivity ?: run {
            return
        }
        //activityResultPromise = promise
        val intent = Intent(reactContext, FaceVerificationActivity::class.java).apply {
                putExtra(FaceVerificationActivity.FACE_DATA_KEY, face_data)
                putExtra(FaceVerificationActivity.USER_FACE_ID_KEY, 1)

        }
        activity.startActivityForResult(intent, 1002)

    }

override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
    
    //Log.d(TAG1, "onActivityResult=========: $requestCode")

    if (requestCode == 1001) {
        if (resultCode == Activity.RESULT_OK) {
           // activityResultPromise?.resolve("success")
            val face_base64 = data?.getStringExtra("data")

            val map = Arguments.createMap().apply {
                        putInt("code", 1)
                        putString("result", "success")
                        putString("face_base64",face_base64)
                }
                  //  promise.resolve(map)
                    
                   // Log.d(TAG1, "sendEvent:$face_base64")

                    sendEvent("Enrolled", map)


        } else {

            val map = Arguments.createMap().apply {
                        putInt("code", 0)
                        putString("result", "fail")
                    
                }

                sendEvent("Enrolled", map)

        }
        activityResultPromise = null
    }
    else if (requestCode == 1002){
        if (resultCode == Activity.RESULT_OK) {
            val face_base64 = data?.getStringExtra("data")

            val map = Arguments.createMap().apply {
                        putInt("code", 1)
                        putString("result", "success")
                        putString("face_base64",face_base64)
                }
            sendEvent("Verified", map)
        }
        else {
            val map = Arguments.createMap().apply {
                        putInt("code", 0)
                        putString("result", "fail")
                    
                }

                sendEvent("Verified", map)

        }

        
    }
}

override fun onNewIntent(intent: Intent) {
    // 可留空
}


    // 处理 Activity 返回结果,depracated
    private fun handleActivityResult(result: ActivityResult) {
       // val promise = activityResultPromise ?: return
        Log.d(TAG1, "handleActivityResult")

        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val data = result.data
                if (data != null) {
                    val resultStr = data.getStringExtra("result")
                    val map = Arguments.createMap().apply {
                        putString("result", resultStr)
                    }
                  //  promise.resolve(map)
                    
                    Log.d(TAG1, "sendEvent:$map")

                    sendEvent("LiveNessResult", map)
                } else {
                  //  promise.reject("DATA_NULL", "返回数据为空")
                }
            }
            Activity.RESULT_CANCELED -> {
              //  promise.reject("USER_CANCEL", "用户取消操作")
                currentActivity?.let {
                    Toast.makeText(it, "操作已取消", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
             //   promise.reject("UNKNOWN_ERROR", "未知错误，结果码: ${result.resultCode}")
            }
        }

        // 清空 Promise，避免内存泄漏
        activityResultPromise = null
    }

    // 发送事件到 JS 层
    private fun sendEvent(eventName: String, params: WritableMap?) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}
    