export interface InitConfig {
  apiKey: string;
  enableLiveness?: boolean;
}

export interface FaceDetectionResult {
  faceId: string;
  confidence: number;
  image: string; // base64
}

export interface FaceRecognitionInterface {
  initializeSDK(config: InitConfig): Promise<string>;
  detectFace(imagePath: string): Promise<FaceDetectionResult>;
  addFace(imagePath: string): Promise<FaceDetectionResult>;

}

