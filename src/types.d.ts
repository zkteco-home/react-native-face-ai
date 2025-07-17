export interface InitConfig {
    apiKey: string;
    enableLiveness?: boolean;
}
export interface FaceDetectionResult {
    faceId: string;
    confidence: number;
    image: string;
}
export interface FaceRecognitionInterface {
    initializeSDK(config: InitConfig): Promise<string>;
    detectFace(imagePath: string): Promise<FaceDetectionResult>;
}
