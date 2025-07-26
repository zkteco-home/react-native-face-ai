package com.faceAI.demo
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.io.ByteArrayOutputStream
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableArray
import android.util.Base64
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.faceAI.demo.FaceAIConfig
import com.faceAI.demo.SysCamera.addFace.AddFaceImageActivity
import androidx.core.content.ContextCompat
import android.content.Intent
import com.faceAI.demo.SysCamera.addFace.AddFaceImageActivity.ADD_FACE_IMAGE_TYPE_KEY
import org.json.JSONObject

class FaceAISDKModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  companion object {
    public var faceCameraViewManager: FaceCameraViewManager? = null
  }


  override fun getName(): String = "FaceAISDK"

  // 示例方法：初始化 SDK（替换为真实 SDK 调用）
  @ReactMethod
  fun initializeSDK(config: ReadableMap, promise: Promise) {
    try {
      // TODO: 替换为真实 SDK 初始化逻辑
      val apiKey = config.getString("apiKey")
      println("Initializing with key: $apiKey")
      Log.d("FaceRecognition", "Initializing with key: $apiKey")
      val reactContext: ReactApplicationContext = getReactApplicationContext()
      val context: Context = reactContext.getApplicationContext()
      FaceAIConfig.init(context)


      promise.resolve("SDK Initialized")
    } catch (e: Exception) {
      promise.reject("INIT_ERROR", e.message)
    }
  }

  // 示例方法：检测人脸（返回 base64 图片）
  @ReactMethod
  fun detectFace(imagePath: String, promise: Promise) {
    try {
      // TODO: 替换为真实人脸检测逻辑
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

  // 示例方法：检测人脸（返回 base64 图片）
  @ReactMethod
  fun addFace(imagePath: String, promise: Promise) {
    try {
      val intent = Intent(reactApplicationContext, AddFaceImageActivity::class.java).apply {
        putExtra("ADD_FACE_IMAGE_TYPE_KEY", AddFaceImageActivity.AddFaceImageTypeEnum.FACE_VERIFY.name)
      }
      val act = currentActivity ?: return
      act.startActivity(intent)
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


  // 发送事件到 JS（可选）
  private fun sendEvent(eventName: String, params: WritableMap?) {
    reactApplicationContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
