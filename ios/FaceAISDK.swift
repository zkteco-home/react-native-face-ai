import Foundation

@objc(RNFaceAISDK)
class RNFaceAISDK: NSObject {
  @objc
  func compareFaces(
    _ image1: String,
    image2Base64 image2: String,
    resolver resolve: @escaping RCTPromiseResolveBlock,
    rejecter reject: @escaping RCTPromiseRejectBlock
  ) {
    // TODO: 替换为真实算法
    resolve(["similarity": 0.87])
  }
}