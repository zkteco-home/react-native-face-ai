#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNFaceAISDK, NSObject)
RCT_EXTERN_METHOD(compareFaces:(NSString *)image1Base64
                  image2Base64:(NSString *)image2Base64
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
@end