package com.faceAI.demo.SysCamera.search

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



import androidx.core.content.edit
import com.faceAI.demo.FaceAISettingsActivity.Companion.FRONT_BACK_CAMERA_FLAG
import com.faceAI.demo.R
import com.faceAI.demo.databinding.ActivityFaceSearchNaviBinding

/**
 * 人脸识别搜索 演示导航Navi，目前支持千张图片秒级搜索
 * 测试验证人脸库图片位于/assert 目录，更多的人脸图片请使用Ai 生成
 *
 * 使用的宽动态（人脸搜索必须大于110DB）高清抗逆光摄像头；保持镜头干净（用纯棉布擦拭油污）
 *
 */
class SearchNaviActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFaceSearchNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceSearchNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            this@SearchNaviActivity.finish()
        }

        binding.systemCameraSearch.setOnClickListener {
            startActivity(Intent(baseContext, FaceSearch1NActivity::class.java))
        }

        binding.systemCameraSearchMn.setOnClickListener {
            startActivity(Intent(baseContext, FaceSearchMNActivity::class.java))
        }

        binding.systemCameraAddFace.setOnClickListener {
            startActivity(
                Intent(baseContext, FaceSearchImageMangerActivity::class.java)
                    .putExtra("isAdd", true)
                    .putExtra("isBinocularCamera", false))
        }

        binding.binocularCameraSearch.setOnClickListener {
           // showConnectUVCCameraDialog()
        }

        binding.binocularCameraAddFace.setOnClickListener {
            startActivity(
                Intent(baseContext, FaceSearchImageMangerActivity::class.java)
                    .putExtra("isAdd", true)
                    .putExtra("isBinocularCamera", true)
            )
        }

        //验证复制图片
        binding.copyFaceImages.setOnClickListener {
            binding.copyFaceImages.visibility= View.INVISIBLE
            Toast.makeText(baseContext, "Copying...", Toast.LENGTH_LONG).show()
           // showAppFloat(baseContext)

        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.switchCamera.setOnClickListener {
            val sharedPref = getSharedPreferences("FaceAISDK_SP", Context.MODE_PRIVATE)
            if (sharedPref.getInt( FRONT_BACK_CAMERA_FLAG, 1) == 1) {
                sharedPref.edit(commit = true) { putInt( FRONT_BACK_CAMERA_FLAG, 0) }
                Toast.makeText(
                    baseContext,
                    "Front camera now",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                sharedPref.edit(commit = true) { putInt( FRONT_BACK_CAMERA_FLAG, 1) }
                Toast.makeText(
                    baseContext,
                    "Back/USB Camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.editFaceImage.setOnClickListener {
            startActivity(
                Intent(baseContext, FaceSearchImageMangerActivity::class.java).putExtra(
                    "isAdd",
                    false
                )
            )
        }

    }




    /**
     * SDK接入方 自行处理权限管理
     */
    private fun checkNeededPermission() {
    }



}