/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import { useState } from 'react';
import { NewAppScreen } from '@react-native/new-app-screen';
import {
  StatusBar,
  StyleSheet,
  useColorScheme,
  View,
  Text,
  Button,
} from 'react-native';
import { FaceRecognitionAPI } from 'react-native-face-recognition';

function App() {
  const [result, setResult] = useState<any>(null);

  const init = async () => {
    try {
      const msg = await FaceRecognitionAPI.initializeSDK({
        apiKey: 'DEMO-KEY',
        enableLiveness: true,
      });
      console.log('SDK返回:', msg);
    } catch (e) {
      console.error(e);
    }
  };

  const pickAndDetect = async () => {
    const pickerRes = { assets: [{ uri: 'ssffs' }] }; //await launchImageLibrary({ mediaType: 'photo', includeBase64: true });
    if (pickerRes.assets?.[0]?.uri) {
      try {
        const res = await FaceRecognitionAPI.detectFace(
          pickerRes.assets[0].uri,
        );
        setResult(res);
      } catch (e) {
        console.error(e);
      }
    }
  };

  const isDarkMode = useColorScheme() === 'dark';

  return (
    <View style={styles.container}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />

      <Text>FaceRecognition Example</Text>
      <Button title="1. 初始化 SDK" onPress={init} />
      <Button title="2. 选择图片并检测" onPress={pickAndDetect} />
      {result && (
        <>
          <Text>FaceID: {result.faceId}</Text>
          <Text>Confidence: {result.confidence}</Text>
        </>
      )}

      <NewAppScreen templateFileName="App.tsx" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
