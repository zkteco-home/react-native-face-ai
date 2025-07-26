package com.faceAI.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit

import com.faceAI.demo.base.BaseActivity
import com.faceAI.demo.databinding.ActivityFaceAiSettingsBinding


/**
 * 前后摄像头，角度切换等参数设置
 *
 * 更多UVC 摄像头参数设置参考 https://blog.csdn.net/hanshiying007/article/details/124118486
 */
class FaceAISettingsActivity : BaseActivity() {
    private lateinit var binding: ActivityFaceAiSettingsBinding

    companion object {
        //系统摄像头相关
        const val FRONT_BACK_CAMERA_FLAG = "cameraFlag"
        const val SYSTEM_CAMERA_DEGREE = "cameraDegree"

        //UVC 相机旋转 镜像管理。神奇，竟然有相机两个不同步，那分开管理
        const val RGB_UVC_CAMERA_DEGREE = "RGB_UVCCameraDegree"
        const val RGB_UVC_CAMERA_MIRROR_H = "RGB_UVCCameraHorizontalMirror"
        const val IR_UVC_CAMERA_DEGREE = "IR_UVCCameraDegree"
        const val IR_UVC_CAMERA_MIRROR_H = "IR_UVCCameraHorizontalMirror"

        //手动选择指定摄像头
        const val RGB_UVC_CAMERA_SELECT = "RGB_UVC_CAMERA_SELECT"
        const val IR_UVC_CAMERA_SELECT = "IR_UVC_CAMERA_SELECT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceAiSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            this@FaceAISettingsActivity.finish()
        }

        val sharedPref = getSharedPreferences("FaceAISDK", Context.MODE_PRIVATE)

        //1.切换系统相机前后
        binding.switchCamera.setOnClickListener {
            if (sharedPref.getInt(FRONT_BACK_CAMERA_FLAG, 1) == 1) {
                sharedPref.edit(commit = true) { putInt(FRONT_BACK_CAMERA_FLAG, 0) }
                Toast.makeText(baseContext, "Front camera now", Toast.LENGTH_SHORT).show()
            } else {
                sharedPref.edit(commit = true) { putInt(FRONT_BACK_CAMERA_FLAG, 1) }
                Toast.makeText(baseContext, "Back/USB Camera", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. 切换系统相机旋转角度
        val degree = sharedPref.getInt(SYSTEM_CAMERA_DEGREE, 3) % 4
        val degreeStr = when (degree) {
            0 -> "0°"
            1 -> "90°"
            2 -> "180°"
            3 -> "270°"
            else -> "0°"
        }
        binding.cameraDegreeText.text = getString(R.string.camera_degree_set) + degreeStr

        /**
         * 共5个值，默认屏幕方向Display.getRotation()和Surface.ROTATION_0,ROTATION_90,ROTATION_180,ROTATION_270
         * {@link Surface.ROTATION_0}
         */
        binding.switchCameraDegree.setOnClickListener {
            val degreeSys = (sharedPref.getInt(SYSTEM_CAMERA_DEGREE, 3) + 1) % 4
            sharedPref.edit(commit = true) { putInt(SYSTEM_CAMERA_DEGREE, degreeSys) }
            val degreeStrSys = when (degreeSys) {
                0 -> "0°"
                1 -> "90°"
                2 -> "180°"
                3 -> "270°"
                else -> "0"
            }
            binding.cameraDegreeText.text = getString(R.string.camera_degree_set) + degreeStrSys
        }



 
    }


    /**
     * 选择摄像头
     */
    private fun selectCamera(cameraName: String,cameraKey: String,sharedPref: SharedPreferences) {
      

    }

}