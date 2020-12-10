public class RecordThread extends Thread
{
    JavaSoundRecorder recorder;

    public RecordThread(JavaSoundRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void run(){
        System.out.println("RecordThread\n");
        recorder.recordAudio();
    }
}
