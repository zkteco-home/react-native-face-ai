/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */
import { useState, useEffect, useRef } from 'react';
import {
  StatusBar,
  StyleSheet,
  useColorScheme,
  View,
  Text,
  Button,
  Platform,
  Image,
} from 'react-native';
import { FaceAI } from 'react-native-face-ai';
import { request, PERMISSIONS, RESULTS } from 'react-native-permissions';

const COLORS = {
  light: {
    background: '#f3f3f3',
    backgroundHighlight: '#cfe6ee',
    cardBackground: '#fff',
    cardOutline: '#dae1e7',
    textPrimary: '#000',
    textSecondary: '#404756',
  },
  dark: {
    background: '#000',
    backgroundHighlight: '#193c47',
    cardBackground: '#222',
    cardOutline: '#444',
    textPrimary: '#fff',
    textSecondary: '#c0c1c4',
  },
};

function App() {
  const [result, setResult] = useState<any>(null);
  const [grantedCamera, setGrantedCamera] = useState(false);
  const [enrollFace, setEnrollFace] = useState('');
  const [verifyFace, setVerifyFace] = useState('');

  let colors = COLORS['light'];
  const init = async () => {
    try {
      const msg = await FaceAI.initializeSDK({
        apiKey: 'DEMO-KEY',
        enableLiveness: true,
      });
      console.log('SDK返回:', msg);
    } catch (e) {
      console.error(e);
    }
  };

  const startEnroll = async () => {
    try {
      const res = await FaceAI.startEnroll();

      setEnrollFace(res.face_base64);
    } catch (e) {
      console.error(e);
    }
  };

  const startVerify = async () => {
    try {
      const res = await FaceAI.startVerify(enrollFace);

      setVerifyFace(res.face_base64);
    } catch (e) {
      console.error(e);
    }
  };

  const isDarkMode = useColorScheme() === 'dark';

  const requestCameraPermission = async () => {
    try {
      const cameraPermission =
        Platform.OS === 'ios'
          ? PERMISSIONS.IOS.CAMERA
          : PERMISSIONS.ANDROID.CAMERA;

      const result = await request(cameraPermission);

      if (result === RESULTS.GRANTED) {
        setGrantedCamera(true);
        return true;
      } else {
        console.log('Camera permission denied');
        return false;
      }
    } catch (err) {
      console.log(err);
      return false;
    }
  };

  useEffect(() => {
    requestCameraPermission();
    init();
  }, []);

  return (
    <View style={styles.container}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />

      <View style={styles.header}>
        <Image
          style={styles.logo}
          source={
            isDarkMode
              ? require('./assets/react-dark.png')
              : require('./assets/react-light.png')
          }
        />
        <Text style={styles.title}>Welcome to React Native</Text>
      </View>
      <Text>FaceRecognition Example</Text>
      <View style={{ margin: 20 }}>
        <Button title="1. Init SDK" onPress={init} />
      </View>

      <View style={{ margin: 20 }}>
        <Button color="#f194ff" title="2. Start Enroll" onPress={startEnroll} />
        {enrollFace && (
          <Image
            style={styles.logo}
            source={{
              uri: enrollFace,
            }}
          />
        )}
      </View>
      <View style={{ margin: 20 }}>
        <Button color="#f194ff" title="2. Start Verify" onPress={startVerify} />
        {verifyFace && (
          <Image
            style={styles.logo}
            source={{
              uri: verifyFace,
            }}
          />
        )}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    alignItems: 'center',
    paddingHorizontal: 24,
  },
  header: {
    width: '100%',
    alignItems: 'center',
    marginTop: 64,
    marginBottom: 48,
  },
  logo: {
    height: 80,
    aspectRatio: 1,
    marginBottom: 24,
  },
  title: {
    fontSize: 24,
    fontWeight: '600',
    marginBottom: 24,
  },
  label: {
    fontSize: 14,
    marginBottom: 8,
  },
});

export default App;
