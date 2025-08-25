package com.faceAI.demo

import android.widget.FrameLayout
import android.graphics.Color
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
import com.faceAI.demo.R;
class FaceCameraViewManager(private val reactContext: ReactApplicationContext): SimpleViewManager<View>(), LifecycleEventListener{
    private var cameraLens: Int = 0
    private var checkLivenessLevel: Int = 0


    init {
        reactContext.addLifecycleEventListener(this)
        FaceAISDKModule.faceCameraViewManager = this
    }


    companion object {
        const val REACT_CLASS = "FaceAICameraView"
    }

    override fun getName():String = "FaceAICameraView"


  override fun createViewInstance(reactContext: ThemedReactContext): View {
    Log.d("TestFaceCameraViewManager", "createViewInstance")

val linearLayout = FrameLayout(reactContext).apply {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(Color.parseColor("#000000"))
        id = View.generateViewId()
    }
    addView(linearLayout)
   linearLayout.post {
        // addCameraFragment(linearLayout,reactContext)
    }

    return linearLayout
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
        val cameraLensFacing = cameraLens
        val degree = 0


        val cameraXBuilder = CameraXBuilder.Builder()
                .setCameraLensFacing(cameraLensFacing) //前后摄像头
                .setLinearZoom(0f)    //焦距范围[0f,1.0f]，参考{@link CameraControl#setLinearZoom(float)}
                .setRotation(degree)      //画面旋转方向
                .setSize(CameraXFragment.SIZE.DEFAULT) //相机的分辨率大小。一般默认就可以
                .create();

        val cameraXFragment = CameraXFragment.newInstance(cameraXBuilder);
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(container.id, cameraXFragment)
                commit()
            }

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