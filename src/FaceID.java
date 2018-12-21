import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.*;
import org.bytedeco.javacv.*;


import Frame.*;

import javax.swing.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

/**
 * @author Murad
 */

public class FaceID {
   OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();

    IplImage img;

    public FaceID() {
        //String pathname = "https://www.youtube.com/watch?v=ydZBBAPE_mw";
        // String pathname = "http://streams.videolan.org/samples/MPEG-4/video.mp4";
//        String pathname = "https://youtu.be/FuK-6gD3h_8";
       String pathname = "/Users/owner/Downloads/videoplayback (1).mp4";
       String im="/Users/owner/Desktop/Images/Zamin Melikov.png";
//        try {


           // OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);

            //FFmpegFrameGrabber grabber=new FFmpegFrameGrabber(pathname);

            //grabber.setAudioStream(0);
           // grabber.start();
            //grabber.setFrameNumber(3000);
            IplImage iplImage= opencv_imgcodecs.cvLoadImage(im);
            IplImage grayImage = IplImage.create(iplImage.width(),
                    iplImage.height(), IPL_DEPTH_8U, 1);

            // We convert the original image to grayscale.
            cvCvtColor(iplImage, grayImage, CV_BGR2GRAY);
            //Frame frame = grabber.grab();
            Frame frame = converter.convert(grayImage);
            CanvasFrame canvasFrame = new CanvasFrame("FaceGrip");


            canvasFrame.setDefaultCloseOperation(3);
            canvasFrame.setSize(frame.imageWidth, frame.imageHeight);
            FaceDetect faceDetect=new FaceDetect();
            while (canvasFrame.isVisible() && (frame = converter.convert(grayImage)) != null) {
                img=converter.convert(frame).clone();
                //faceDetect(img);
                faceDetect.faceRectangleAndText(img,faceDetect.faceDetect(img,1.3,4,CV_HAAR_DO_CANNY_PRUNING),"ddd");
                canvasFrame.showImage(converter.convert(img));
            }

//        } catch (FrameGrabber.Exception ex) {
//            Logger.getLogger(FaceID.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



    public static void main(String[] args) {
       //new FaceID();
        new FaceDetectAndSave("/Users/owner/Desktop/Images/",System.getProperty("user.home")+"/Desktop/face_images/",
                240,240);
    }

}


