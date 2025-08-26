/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */
import { useState, useEffect,useRef } from 'react';
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
import { FaceRecognitionAPI,FaceAISDKView,subscribeToEvents  } from 'react-native-face-recognition';
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


const FaceRecognitionView = () => {

    const sdkViewRef = useRef(null);

    useEffect(() => {
     }, []);

    const startCamera = async () => {
      
    };

    const stopCamera = async () => {
       
    };

    const handleViewLayout = () => {
    };

    return (
        <View style={styles.container}>
            <View  style={{width:200,height:300}} onLayout={handleViewLayout}>
                <FaceAISDKView
                    style={{flex:1}}
                    livenessLevel={1}
                    cameraLens={0}
                />
            </View>
        </View>
    );
};

function App() {
  const [result, setResult] = useState<any>(null);
  const [grantedCamera, setGrantedCamera] = useState(false);



  let colors = COLORS['light'];
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
    const pickerRes = { assets: [{ uri: 'ssffs' }] }; 
    if (pickerRes.assets?.[0]?.uri) {
      try {
        const res = await FaceRecognitionAPI.addFace(
          pickerRes.assets[0].uri,
        );
        setResult(res);
      } catch (e) {
        console.error(e);
      }
    }
  };


  const startLiveNess = async () => {
    const pickerRes = { assets: [{ uri: 'ssffs' }] }; 
    if (pickerRes.assets?.[0]?.uri) {
      try {
        const res = await FaceRecognitionAPI.startLiveNess(
          pickerRes.assets[0].uri,
        );
        setResult(res);
      } catch (e) {
        console.error(e);
      }
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

  useEffect(() => {
    // 1. 启动 mock 事件
   // FaceRecognitionAPI.startMockEvents();

    // 2. 订阅
    const sub = subscribeToEvents((evt) => {
      console.log('收到原生事件:', evt);
    });

    // 3. 清理
    return () => sub.remove();
  }, []);



  function getHermesLabel(){

    return <Text style={styles.label}>JS Engine: Hermes</Text>;
  }

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
        
        {getHermesLabel()}
      </View>
      <Text>FaceRecognition Example</Text>
      <Button title="1. Init SDK" onPress={init} />
          <View style={{height:20}}></View>
      <Button title="2. AddImage" onPress={pickAndDetect} />
      {result && (
        <>
          <Text>FaceID: {result.faceId}</Text>
          <Text>Confidence: {result.confidence}</Text>
        </>
      )}

      <View style={{height:20}}></View>
      <Button title="3. StartLive" onPress={startLiveNess} />

   
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
  callout: {
    width: '100%',
    maxWidth: 320,
    marginTop: 36,
    paddingVertical: 16,
    paddingHorizontal: 20,
    paddingLeft: 16,
    borderRadius: 12,
    fontSize: 16,
    textAlign: 'center',
  },
  calloutEmphasis: {
    fontWeight: 'bold',
  },
  linksContainer: {
    flex: 1,
    flexWrap: 'wrap',
    flexDirection: 'row',
    justifyContent: 'center',
    columnGap: 12,
    rowGap: 12,
    maxWidth: 800,
    marginBottom: 48,
  },
  linksTitle: {
    width: '100%',
    fontSize: 18,
    fontWeight: '600',
    textAlign: 'center',
    marginBottom: 20,
  },
  link: {
    width: '100%',
    paddingVertical: 20,
    paddingHorizontal: 24,
    borderRadius: 12,
    borderWidth: 1,
    boxShadow: '0 4px 8px rgba(0, 0, 0, .03)',
  },
  linkText: {
    marginBottom: 4,
    fontSize: 16,
    fontWeight: '600',
  },
});

export default App;
