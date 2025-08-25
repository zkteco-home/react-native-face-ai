import { NativeModules, NativeEventEmitter,requireNativeComponent,UIManager } from 'react-native';
import type { FaceRecognitionInterface, InitConfig, FaceDetectionResult } from './types';

 const LINKING_ERROR =
   `The package 'react-native-face-recognition' doesn't seem to be linked. Make sure: \n\n` +
   '- You rebuilt the app after installing the package\n' +
   '- You are not using Expo Go\n';

type FaceAISDKProps = {
  cameraLens: number;
  livenessLevel: number;
};

const ComponentName = 'FaceAICameraView';
export const FaceAISDKView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<FaceAISDKProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };

const { FaceAISDK } = NativeModules;
const faceRecognitionEmitter = new NativeEventEmitter(FaceAISDK);

export const FaceRecognitionAPI: FaceRecognitionInterface = {
  initializeSDK: (config: InitConfig) => FaceAISDK.initializeSDK(config),
  detectFace: (imagePath: string) => FaceAISDK.detectFace(imagePath),
  addFace: (imagePath: string) => FaceAISDK.addFace(imagePath),
  startLiveNess: (imagePath: string) => FaceAISDK.addFace(imagePath),

};

// 可选：监听原生事件
export const subscribeToEvents = (callback: (event: any) => void) => {
  return faceRecognitionEmitter.addListener('FaceRecognitionEvent', callback);
};

export type { InitConfig, FaceDetectionResult };