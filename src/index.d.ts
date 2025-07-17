import type { FaceRecognitionInterface, InitConfig, FaceDetectionResult } from './types';
export declare const FaceRecognitionAPI: FaceRecognitionInterface;
export declare const subscribeToEvents: (callback: (event: any) => void) => import("react-native").EmitterSubscription;
export type { InitConfig, FaceDetectionResult };
