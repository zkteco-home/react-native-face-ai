<div align=center>
<img src="https://github.com/user-attachments/assets/b1e0a9c4-8b43-4eb8-bf7a-7632901cfb2c" width = 19%  alt="点击查看详情"/>
</div>

<img src="https://badgen.net/badge/FaceAI%20SDK/%20%E5%BF%AB%E9%80%9F%E5%AE%9E%E7%8E%B0%E4%BA%BA%E8%84%B8%E8%AF%86%E5%88%AB%E5%8A%9F%E8%83%BD" />

## [About「FaceAI SDK」](https://github.com/FaceAISDK/FaceAISDK_Android)

FaceAI SDK is on_device Offline Face Detection 、Recognition 、Liveness Detection Anti Spoofing and 1:N/M:N Face Search SDK

# react-native-face-recognition Plugin with Liveness Detection - Fully On Premise
## Overview
This repository demonstrates both `face liveness detection` and `face recognition` technology for `React-Native` on `Android` and `iOS` platforms.

> In this repository, we integrated `FaceAI SDK`'s `face liveness detection` and `face recognition` technology into the `React-Native` project for both `Android` and `iOS`.</br>

<br>This is `on-premise face recognition SDK` which means everything is processed in your phone and **NO** data leaves the device.
<br></br>


<br></br>




## Support

This library follows the React Native [releases support policy](https://github.com/reactwg/react-native-releases/blob/main/docs/support.md).<br>
It is supporting the **latest version**, and the **two previous minor series**.

## Setup

```bash
$ npm install --save react-native-face-recognition
# --- or ---
$ yarn add react-native-face-recognition


## API Usages
#### 2.1 FaceRecognitionAPI
  - Activate the `FaceRecognitionAPI` by calling the `setActivation` method:
  ```kotlin
        var ret = await FaceRecognitionAPI.setActivation("...");
        console.log("set activation:", ret);
  ```
  - Initialize the `FaceRecognitionAPI` by calling the `initSDK` method:
  ```kotlin
      var ret = await FaceRecognitionAPI.initSDK();
  ```
  - Set parameters using the  `setParam` method:
  ```kotlin
    var ret = await FaceRecognitionAPI.setParam(checkLivenessLevel);
  ```
  - Extract faces using the `extractFaces` method:
  ```kotlin
    var faceBoxes = await FaceRecognitionAPI.extractFaces(uri);
  ```
  - Calculate the similarity between faces using the `similarityCalculation` method:
  ```kotlin
    const similarity = await FaceRecognitionAPI.similarityCalculation(
        face.templates,
        person.templates
    );
  ```
### FaceRecognitionAPI
  - To build the native camera screen and process face detection, please refer to the [example/App.tsx](https://github.com/zkteco-home/react-native-face-recognition/tree/master/example/App.tsx) file in the repository. 




