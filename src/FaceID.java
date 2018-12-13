
import java.text.Format;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacv.*;

/**
 * @author Murad
 */

public class FaceID {
    public FaceID() {
        String pathname = "http://62.163.246.48:50001/cgi-bin/faststream.jpg?stream=half&fps=15&rand=COUNTER";
       // String pathname = "http://streams.videolan.org/samples/MPEG-4/video.mp4";
//        String pathname = "https://youtu.be/FuK-6gD3h_8";
//        String pathname = "E:\\MOV_22051.mp4";
        try {

           // System.out.println(System.getProperty("java.library.path"));


            //OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
            FFmpegFrameGrabber grabber=new FFmpegFrameGrabber(pathname);
            grabber.start();

            Frame frame = grabber.grab();
            CanvasFrame canvasFrame = new CanvasFrame("FaceGrip");
            canvasFrame.setDefaultCloseOperation(3);
            canvasFrame.setSize(frame.imageWidth, frame.imageHeight);
            while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
                canvasFrame.showImage(frame);
            }

        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(FaceID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new FaceID();
    }

}


