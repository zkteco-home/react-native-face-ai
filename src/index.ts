import { NativeModules, NativeEventEmitter } from 'react-native';
import type { FaceRecognitionInterface, InitConfig, FaceDetectionResult } from './types';

const { FaceAISDK } = NativeModules;
const faceRecognitionEmitter = new NativeEventEmitter(FaceAISDK);

export const FaceRecognitionAPI: FaceRecognitionInterface = {
  initializeSDK: (config: InitConfig) => FaceAISDK.initializeSDK(config),
  detectFace: (imagePath: string) => FaceAISDK.detectFace(imagePath),
};

// 可选：监听原生事件
export const subscribeToEvents = (callback: (event: any) => void) => {
  return faceRecognitionEmitter.addListener('FaceRecognitionEvent', callback);
};

export type { InitConfig, FaceDetectionResult };