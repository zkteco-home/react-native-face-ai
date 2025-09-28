export interface InitConfig {
  apiKey: string;
  enableLiveness?: boolean;
}

export interface FaceDetectionResult {
  faceId: string;
  confidence: number;
  image: string; // base64
}

export interface IEnrollEvent {
  code: 0 | 1 | number;   // 0 失败，1 成功，其它异常
  [k: string]: unknown;   // 允许更多字段
}

type EnrollSuccessCb = (event: IEnrollEvent) => void;
type EnrollFailCb    = (event: IEnrollEvent) => void;


export interface FaceRecognitionInterface {
  initializeSDK(config: InitConfig): Promise<string>;
  detectFace(imagePath: string): Promise<FaceDetectionResult>;
  addFace(imagePath: string): Promise<FaceDetectionResult>;
  startEnroll(onSuccess?: EnrollSuccessCb,
    onFail?: EnrollFailCb): Promise<IEnrollEvent>;
  startVerify(face_data:string,onSuccess?: EnrollSuccessCb,
    onFail?: EnrollFailCb): Promise<IEnrollEvent>;

}

