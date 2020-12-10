import com.dropbox.core.v2.DbxClientV2;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JavaSoundRecorder
{
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    DbxClientV2 client;
    AudioFormat format;
    private int milliseconds = 0;

    public JavaSoundRecorder(DbxClientV2 client, int milliseconds)
    {
        this.milliseconds = milliseconds;
        this.client = client;
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(
            sampleRate,
            sampleSizeInBits,
            channels,
            signed,
            bigEndian
        );
        return format;
    }

    public void recordAudio()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String soundName = formatter.format(now);
        String fileName = "C:\\Users\\Public\\" + soundName + ".wav";
        File file = new File(fileName);
        start(file);
        stop(milliseconds, file, soundName);
    }


    void start(File file)
    {
        Thread thread = new Thread(){
            @Override
            public void run()
            {
                try {
                    line.open(format);
                    line.start();   // start capturing
                    AudioInputStream ais = new AudioInputStream(line);
                    AudioSystem.write(ais, fileType, file);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        thread.start();
    }


    void stop(int milliseconds, File file, String soundName)
    {
        Thread thread = new Thread(){
            @Override
            public void run()
            {
                try {
                    sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                line.stop();
                line.close();
                try {
                    InputStream is = new FileInputStream(file);
                    client.files().uploadBuilder("/"+ soundName + ".wav")
                            .uploadAndFinish(is);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (file.exists()) {
                    file.delete();
                } else {
                    System.err.println(
                            "I cannot find '" + file + "' ('" + file.getAbsolutePath() + "')");
                }
            }
        };
        thread.start();
    }
    int getMilliseconds(){
        return milliseconds;
    }
}