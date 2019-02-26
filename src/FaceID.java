import dao.Dao;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;
import project.*;
import utility.Const;
import utility.EyeCenterAndAngle;

import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_objdetect.*;

/**
 * @author Murad
 */

public class FaceID{
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        
        IplImage img;
        
        public FaceID() throws FrameGrabber.Exception{
                new Dao().getNumberAndNameFromDB();
                //String pathname = "https://www.youtube.com/watch?v=ydZBBAPE_mw";
                // String pathname = "http://streams.videolan.org/samples/MPEG-4/video.mp4";
                //        String pathname = "https://youtu.be/FuK-6gD3h_8";
                String pathname = "C:\\Users\\xakus\\Desktop\\Images\\DSC_2068.MOV";
                String im       = "/Users/owner/Desktop/Images/Niyal Huseynova.png";
                //        try {
                
                
                ///IP_CAM_AND_VIDEO
//                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(pathname);
//                  grabber.setAudioStream(2);
//                  grabber.setAudioChannels(2);
//                  grabber.setFormat("mp4");
//                grabber.start();
               // grabber.setFrameNumber(400);
                
                
//                Frame frame = grabber.grab();
                //////////
                
                //WEB_CAMERA
                        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
                        grabber.start();
                        Frame frame = grabber.grab();
                /////
                
                //IMAGE
                //          IplImage iplImage= cvLoadImage(im);
                //
                //          Frame frame = converter.convert(iplImage);
                //////////////////
                
                
                CanvasFrame canvasFrame = new CanvasFrame("FaceGrip");
                
                
                canvasFrame.setDefaultCloseOperation(3);
                canvasFrame.setSize(frame.imageWidth, frame.imageHeight);
                FaceDetect faceDetect = new FaceDetect();
                EyeDetect  eyeDetect  = new EyeDetect();
                while (canvasFrame.isVisible() && (frame = grabber.grab()) != null && frame != null && !grabber.isDeinterlace()/*(frame = converter.convert(iplImage)) != null*/){
                        
                        img = converter.convert(frame);
                        
                        
                        //           List<Rect> rects= faceDetect.faceDetect2(img);
                        //            faceDetect.faceRectangleAndText2(img,rects);
                        
                        //Eye
                        //List<opencv_core.Rect> rects =eyeDetect.eyeDetect2(img);
                        // EyeCenterAndAngle angle=Operation.angle(rects);
                        // System.out.println(angle.getAngle()+"");
                        //  img = Operation.rotate(img,angle.getCenterPoint(),angle.getAngle());
                        
                        //
                        CvSeq seq = faceDetect.faceDetect(img, 1.3, 5, CV_HAAR_SCALE_IMAGE);
                        faceDetect.faceRectangleAndText(img, seq);
                        // eyeDetect.eyeRectangle(img,rects);
                        canvasFrame.showImage(converter.convert(img));
                        
                }
                
                //        } catch (FrameGrabber.Exception ex) {
                //            Logger.getLogger(FaceID.class.getName()).log(Level.SEVERE, null, ex);
                //        } catch (IOException e) {
                //            e.printStackTrace();
                //        }
        }
        
        
        public static void main(String[] args) throws FrameGrabber.Exception{
                
                new FaceDetectAndSave(Const.PATH_TO_THE_FOLDER_WITH_RAW_PICTURES);
                FaceRecognizingAndTraning faceRecognizingAndTraning = new FaceRecognizingAndTraning();
                faceRecognizingAndTraning.traning();
                new FaceID();
                
        }
        
}


