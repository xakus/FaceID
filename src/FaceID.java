
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacv.*;

/**
 * @author Murad
 */

public class FaceID {
    public FaceID() {
        String pathname = "E:\\MOV_22051";
        try {

            System.out.println(System.getProperty("java.library.path"));


            OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
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


