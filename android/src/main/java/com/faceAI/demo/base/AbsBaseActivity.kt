package com.faceAI.demo.base

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.faceAI.demo.R


/**
 * 相机权限管理
 *
 */
open class AbsBaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /**
     * 统一全局的拦截权限请求，给提示
     *
     */
    private fun checkNeededPermission() {
        //存储照片在某些目录需要,Manifest.permission.WRITE_EXTERNAL_STORAGE
    }





}