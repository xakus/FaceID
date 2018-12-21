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
    String outPath;
    public FaceDetectAndSave(String inPath,String outPath,int faceSaveW,int faceSaveH){
        this.inPath=inPath;
        this.outPath=outPath;

        File root=new File(inPath);
        FilenameFilter filter =new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        File[] imageFiles =root.listFiles(filter);
        FaceOperation faceOperation=new FaceOperation(outPath,faceSaveW,faceSaveH);
        for (File image:imageFiles){
            opencv_core.IplImage img=cvLoadImage(image.getAbsolutePath());
            opencv_core.IplImage grayImage = opencv_core.IplImage.create(img.width(),
                    img.height(), IPL_DEPTH_8U, 1);

            // We convert the original image to grayscale.
            cvCvtColor(img, grayImage, CV_BGR2GRAY);
            String name = image.getName().split("\\.")[0];
            System.out.println("Name= "+name);
            FaceDetect faceDetect =new FaceDetect();
            faceDetect.faceDetect(grayImage,1.3,4, opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING);
            List<opencv_core.CvRect> cvRects = faceDetect.getCvRect();
            for(opencv_core.CvRect cvRect:cvRects){
                faceOperation.faceCutAndSave(grayImage,name,cvRect.x(),cvRect.y(),cvRect.width(),cvRect.height());
                System.out.println("Name= "+name);
            }
        }
    }
}
