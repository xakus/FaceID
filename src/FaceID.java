
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.*;


import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

/**
 * @author Murad
 */

public class FaceID {
    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    IplImage img;

    public FaceID() throws FrameGrabber.Exception {
        //String pathname = "https://www.youtube.com/watch?v=ydZBBAPE_mw";
        // String pathname = "http://streams.videolan.org/samples/MPEG-4/video.mp4";
//        String pathname = "https://youtu.be/FuK-6gD3h_8";
//        String pathname = "/Users/owner/Downloads/videoplayback (1).mp4";
        String im = "/Users/owner/Desktop/Images/Nermin Hebibova#7.jpg";
//        try {


        ///IP_CAM_AND_VIDEO
//            FFmpegFrameGrabber grabber=new FFmpegFrameGrabber(pathname);
//            grabber.setAudioStream(0);
//            grabber.start();
//            grabber.setFrameNumber(4000);
//
//
//
//           Frame frame = grabber.grab();
        //////////

        //WEB_CAMERA
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        Frame frame = grabber.grab();
        /////

        //IMAGE
//          IplImage iplImage= opencv_imgcodecs.cvLoadImage(im);
//
//          Frame frame = converter.convert(iplImage);
        //////////////////


        CanvasFrame canvasFrame = new CanvasFrame("FaceGrip");


        canvasFrame.setDefaultCloseOperation(3);
        canvasFrame.setSize(frame.imageWidth, frame.imageHeight);
        FaceDetect faceDetect = new FaceDetect();

        while (canvasFrame.isVisible() && (frame = grabber.grab()) != null && frame!=null && !grabber.isDeinterlace()/*(frame = converter.convert(iplImage)) != null*/) {
            img = converter.convert(frame);
            //faceDetect(img);
            CvSeq seq = faceDetect.faceDetect(img, 1.4, 5, CV_HAAR_FIND_BIGGEST_OBJECT);
//System.out.println(System.currentTimeMillis());
            faceDetect.faceRectangleAndText(img, seq);

            canvasFrame.showImage(converter.convert(img));


        }

//        } catch (FrameGrabber.Exception ex) {
//            Logger.getLogger(FaceID.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) throws FrameGrabber.Exception {

         //new FaceDetectAndSave("/Users/owner/Desktop/Images/");
        //FaceRecognizingAndTraning faceRecognizingAndTraning =new FaceRecognizingAndTraning();
        // faceRecognizingAndTraning.traning();
        new FaceID();
    }

}


