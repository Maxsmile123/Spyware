import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String ACCESS_TOKEN_SOUND = "3L_WwvKD2X4AAAAAAAAAAasOXKR9X-iOZKcisMKh7n124TM2Z87kFfN1z5JYfwrY";
        String ACCESS_TOKEN_SCREEN = "4Oy8XNGN3QwAAAAAAAAAAUMmM2ync3xQADM2TwEuTUiLtEAPTRpxPMEsrgEhtZJa";


        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        long startTime = System.currentTimeMillis();
        int counter1,counter2;
        counter1 = counter2 = 0;
        long time = 0;
        while (true) {
            DbxClientV2 client1 = new DbxClientV2(config, ACCESS_TOKEN_SOUND);
            JavaSoundRecorder recorder = new JavaSoundRecorder(client1, 10000);
            DbxClientV2 client2 = new DbxClientV2(config, ACCESS_TOKEN_SCREEN);
            Screen thread = new Screen(client2);
            if(time == 0){
                thread.start();
                recorder.recordAudio();
            }
            time = System.currentTimeMillis() - startTime;
            if(time - (5000*counter1) >= 5000){
                thread.start();
                counter1++;
            }
            if(time - (11500*counter2) >= 11500){
                recorder.recordAudio();
                counter2++;
            }
        }
    }
}