
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_objdetect.*;
import org.bytedeco.javacv.*;


import javax.swing.*;

import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_MAGIC_VAL;
import static org.bytedeco.javacpp.opencv_objdetect.cvHaarDetectObjects;
/**
 * @author Murad
 */

public class FaceID {
   OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
   CvHaarClassifierCascade  cascade;
    IplImage img;
   String f="C:\\Users\\Murad\\Documents\\GitHub\\FaceID\\face.xml";
    public FaceID() {
        //String pathname = "https://www.youtube.com/watch?v=ydZBBAPE_mw";
        // String pathname = "http://streams.videolan.org/samples/MPEG-4/video.mp4";
//        String pathname = "https://youtu.be/FuK-6gD3h_8";
//        String pathname = "E:\\MOV_22051.mp4";
        try {

            // System.out.println(System.getProperty("java.library.path"));

            cascade=new CvHaarClassifierCascade(cvLoad(f));
            OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
            //  FFmpegFrameGrabber grabber=new FFmpegFrameGrabber(pathname);

            grabber.start();

            Frame frame = grabber.grab();
            CanvasFrame canvasFrame = new CanvasFrame("FaceGrip");
            JButton jButton=new JButton("press");
            canvasFrame.add(jButton);
            canvasFrame.setDefaultCloseOperation(3);
            canvasFrame.setSize(frame.imageWidth, frame.imageHeight);
            while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
                img=converter.convert(frame);
                faceDetect(img);
                canvasFrame.showImage(converter.convert(img));
            }

        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(FaceID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void faceDetect(IplImage img){
      CvMemStorage memStorage =CvMemStorage.create();
       CvSeq face=cvHaarDetectObjects(img,cascade,memStorage,1.5,3,CV_HAAR_DO_CANNY_PRUNING);
       int total = face.total();
       if(total>0){
           System.out.println(total);
           for(int i=0;i<total;i++){
               CvRect rect =new CvRect(cvGetSeqElem(face,i));
               int x=rect.x(),y=rect.y(),w=rect.width(),h=rect.height();
               rectangle(cvarrToMat(img),new Rect(x,y,w,h),new Scalar(0,255,0,0),2,0,0);
            }
       }
    }
    public static void main(String[] args) {
        new FaceID();
    }

}


