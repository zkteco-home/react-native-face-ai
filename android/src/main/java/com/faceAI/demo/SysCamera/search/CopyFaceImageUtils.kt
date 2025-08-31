package com.faceAI.demo.SysCamera.search

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import com.faceAI.demo.FaceImageConfig.CACHE_SEARCH_FACE_DIR
import com.ai.face.faceSearch.search.FaceSearchImagesManger
import com.airbnb.lottie.LottieAnimationView
import com.faceAI.demo.R

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

/**
 * 拷贝200张工程目录Src/main/Assert下的人脸测试图方便你验证效果
 * 需要更多验证人脸图请邮箱联系，我们提高3000张图
 *
 * 封装Utils供Java 代码调用。使用Kotlin 协程能极大的简化代码结构
 *
 * 网盘分享的3000 张人脸图链接: https://pan.baidu.com/s/1RfzJlc-TMDb0lQMFKpA-tQ?pwd=Face 提取码: Face
 * 可复制工程目录 ./faceAILib/src/main/assert 下后在Demo 的人脸库管理页面一键导入模拟插入多张人脸图
 *
 */
class CopyFaceImageUtils {

    companion object {

        interface Callback {
            fun onSuccess()
            fun onFailed(msg: String)
        }

        /**
         * 快速复制工程目录 ./app/src/main/assert目录下200+张 人脸图入库
         * 人脸图规范要求 大于 300*300（人脸部分区域大于200*200）的光线充足无遮挡的正面人脸如（./images/face_example.jpg)
         * 网盘分享的3000 张人脸图链接: https://pan.baidu.com/s/1RfzJlc-TMDb0lQMFKpA-tQ?pwd=Face 提取码: Face
         *
         * @param context
         * @param callBack
         */





        /**
         * 读取Assert 目录的测试验证人脸图
         */
        private fun getBitmapFromAsset(assetManager: AssetManager, strName: String): Bitmap? {
            val istr: InputStream
            var bitmap: Bitmap?
            try {
                istr = assetManager.open(strName)
                bitmap = BitmapFactory.decodeStream(istr)
            } catch (e: IOException) {
                return null
            }
            return bitmap
        }

    }

}