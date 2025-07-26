package com.faceAI.demo

import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.ai.face.base.view.camera.CameraXBuilder
import com.ai.face.base.view.CameraXFragment
import android.view.View
import com.facebook.react.uimanager.annotations.ReactProp
import android.util.Log
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import android.widget.RelativeLayout

class FaceCameraViewManager(private val reactContext: ReactApplicationContext): SimpleViewManager<View>(), LifecycleEventListener{
    private var cameraLens: Int = 0
    private var checkLivenessLevel: Int = 0
    private var linearLayout: FrameLayout? = null

    init {
        reactContext.addLifecycleEventListener(this)
        FaceAISDKModule.faceCameraViewManager = this
    }


    companion object {
        const val REACT_CLASS = "FaceAICameraView"
    }

    override fun getName() = "FaceAICameraView"


  override fun createViewInstance(reactContext: ThemedReactContext): View {
    Log.d("TestFaceCameraViewManager", "createViewInstance")
    linearLayout = FrameLayout(reactContext).apply {
      layoutParams = FrameLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT
      )
      

    }

    return linearLayout!!
  }

  @ReactProp(name = "livenessLevel")
  fun setLivenessLevel(view: View, livenessLevel: Int) {
    Log.d("TestEngine", "setLivenessLevel " + livenessLevel)
    this.checkLivenessLevel = livenessLevel

    //startCamera()
  }


    @ReactProp(name = "cameraLens")
    fun setCameraLens(view: View, cameraLens: Int) {
        Log.d("TestEngine", "setCameraLens " + cameraLens)
        this.cameraLens = cameraLens
    }



    private fun addCameraFragment(container: FrameLayout, reactContext: ThemedReactContext) {
        val activity = reactContext.currentActivity as? FragmentActivity ?: return
        //val fragment = CameraXFragment.newInstance(CameraXBuilder())
        val cameraLensFacing = 0
        val degree = 90
        val cameraXBuilder = CameraXBuilder.Builder()
                .setCameraLensFacing(cameraLensFacing) //前后摄像头
                .setLinearZoom(0.0001f)    //焦距范围[0f,1.0f]，参考{@link CameraControl#setLinearZoom(float)}
                .setRotation(degree)      //画面旋转方向
                .setSize(CameraXFragment.SIZE.DEFAULT) //相机的分辨率大小。一般默认就可以
                .create();

        val cameraXFragment = CameraXFragment.newInstance(cameraXBuilder);



        activity.supportFragmentManager
            .beginTransaction()
            .replace(container.id, cameraXFragment)
            .commitAllowingStateLoss()
    }

    override fun onHostResume() {
        Log.d("TestFaceCameraViewManager", "onHostResume")
    }

    override fun onHostPause() {
        Log.d("TestFaceCameraViewManager", "onHostPause")
 
    }

    override fun onHostDestroy() {
        Log.d("TestFaceCameraViewManager", "onHostDestroy")
    }



}