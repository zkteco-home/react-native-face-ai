"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.subscribeToEvents = exports.FaceRecognitionAPI = void 0;
const react_native_1 = require("react-native");
const { FaceAISDK } = react_native_1.NativeModules;
const faceRecognitionEmitter = new react_native_1.NativeEventEmitter(FaceAISDK);
exports.FaceRecognitionAPI = {
    initializeSDK: (config) => FaceAISDK.initializeSDK(config),
    detectFace: (imagePath) => FaceAISDK.detectFace(imagePath),
};
// 可选：监听原生事件
const subscribeToEvents = (callback) => {
    return faceRecognitionEmitter.addListener('FaceRecognitionEvent', callback);
};
exports.subscribeToEvents = subscribeToEvents;
//# sourceMappingURL=index.js.map