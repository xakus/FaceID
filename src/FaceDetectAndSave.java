import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

public class FaceDetectAndSave {
    String inPath;
    public FaceDetectAndSave(String inPath){
        this.inPath=inPath;

        File root=new File(inPath);
        FilenameFilter filter =new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png")|| name.endsWith(".PNG");
            }
        };
        File[] imageFiles =root.listFiles(filter);
        FaceOperation faceOperation=new FaceOperation();
        for (File image:imageFiles){
            opencv_core.IplImage img=cvLoadImage(image.getAbsolutePath());

            String name = image.getName().split("\\.")[0];

            FaceDetect faceDetect =new FaceDetect();
            faceDetect.faceDetect(img,1.4,4, opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT);
            List<opencv_core.CvRect> cvRects = faceDetect.getCvRect();
            for(opencv_core.CvRect cvRect:cvRects){
             opencv_core.IplImage image1= faceOperation.faceCut(img,cvRect.x(),cvRect.y(),cvRect.width(),cvRect.height());
                opencv_core.IplImage image2=faceOperation.resizeImage(image1);
                faceOperation.saveImage(image2,name);
                System.out.println("Name= "+name);
            }
        }
    }
}
