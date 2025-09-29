import { NativeModules, NativeEventEmitter } from 'react-native';
import type { FaceRecognitionInterface, InitConfig, FaceDetectionResult } from './types';

 const LINKING_ERROR =
   `The package 'react-native-face-recognition' doesn't seem to be linked. Make sure: \n\n` +
   '- You rebuilt the app after installing the package\n' +
   '- You are not using Expo Go\n';

type FaceAISDKProps = {
  cameraLens: number;
  livenessLevel: number;
};


const { FaceAISDK } = NativeModules;
const faceRecognitionEmitter = new NativeEventEmitter(FaceAISDK);


const startEnrollAsync = (
  format?:'jpg'|'png',
  onSuccess?: (event: any) => void,
  onFail?: (event: any) => void
): Promise<any> => {
  return new Promise((resolve, reject) => {
    const subscription = faceRecognitionEmitter.addListener('Enrolled', (event:any) => {
      if (event.code === 1) {
        onSuccess?.(event);
        resolve(event);
      } else if (event.code === 0) {
        onFail?.(event);
        reject(event);
      } else {
        reject(event);
      }
      subscription.remove();
    });
    try {
      FaceAISDK.startEnroll(format);
    } catch (err) {
      subscription.remove();
      reject(err);
    }
  });
};

const startVerifyAsync = (
  face_data:string,
  onSuccess?: (event: any) => void,
  onFail?: (event: any) => void
): Promise<any> => {
  return new Promise((resolve, reject) => {
    const subscription = faceRecognitionEmitter.addListener('Verified', (event:any) => {
      if (event.code === 1) {
        onSuccess?.(event);
        resolve(event);
      } else if (event.code === 0) {
        onFail?.(event);
        reject(event);
      } else {
        reject(event);
      }
      subscription.remove();
    });
    try {
      FaceAISDK.startVerify(face_data);
    } catch (err) {
      subscription.remove();
      reject(err);
    }
  });
};
export const FaceAI: FaceRecognitionInterface = {
  initializeSDK: (config: InitConfig) => FaceAISDK.initializeSDK(config),
  detectFace: (imagePath: string) => FaceAISDK.detectFace(imagePath),
  addFace: (imagePath: string) => FaceAISDK.addFace(imagePath),
  startEnroll: (format?:'jpg'|'png',onSuccess?: (event: any) => void,
  onFail?: (event: any) => void) => startEnrollAsync(format,onSuccess,onFail),
  startVerify: (face_data:string,onSuccess?: (event: any) => void,
  onFail?: (event: any) => void) => startVerifyAsync(face_data,onSuccess,onFail),

};

// 可选：监听原生事件
//export const onEnrolled = (callback: (event: any) => void) => {
//  return faceRecognitionEmitter.addListener('Enrolled', callback);
///};

export type { InitConfig, FaceDetectionResult };