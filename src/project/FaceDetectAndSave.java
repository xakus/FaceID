package project;

import dao.Dao;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import utility.Const;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

public class FaceDetectAndSave {
    String inPath;

    public FaceDetectAndSave(String inPath) {
        this.inPath = inPath;
        clearDir();
        File root = new File(inPath);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png") || name.endsWith(".PNG");
            }
        };
        File[] imageFiles = root.listFiles(filter);
        FaceOperation faceOperation = new FaceOperation();
        for (File image : imageFiles) {
            opencv_core.IplImage img = cvLoadImage(image.getAbsolutePath());

            String name = image.getName().split("\\.")[0];

            FaceDetect faceDetect = new FaceDetect();
            faceDetect.faceDetect(img, 1.3, 5, opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT);
            List<opencv_core.CvRect> cvRects = faceDetect.getCvRect();
            for (opencv_core.CvRect cvRect : cvRects) {
                double size=5.0;
                opencv_core.IplImage image1 = faceOperation.faceCut(img, (int)(cvRect.x()), (int)(cvRect.y()), (int)(cvRect.width()), (int)(cvRect.height()));
                opencv_core.IplImage image2 = faceOperation.resizeImage(image1);

                faceOperation.saveImage(image2, name);
                System.out.println("Name= " + name);
            }

        }
        new Dao().setNumberAndNameToDB();
    }

    public FaceDetectAndSave(String inPath,int i) {
        this.inPath = inPath;
        clearDir();
        File root = new File(inPath);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png") || name.endsWith(".PNG");
            }
        };
        File[] imageFiles = root.listFiles(filter);
        FaceOperation faceOperation = new FaceOperation();
        for (File image : imageFiles) {
            opencv_core.IplImage img = cvLoadImage(image.getAbsolutePath());

            String name = image.getName().split("\\.")[0];

            FaceDetect faceDetect = new FaceDetect();

            List<opencv_core.Rect> rects = faceDetect.faceDetect2(img);
            for (opencv_core.Rect cvRect :rects) {
                opencv_core.IplImage image1 = faceOperation.faceCut(img, cvRect.x(), cvRect.y(), cvRect.width(), cvRect.height());
                opencv_core.IplImage image2 = faceOperation.resizeImage(image1);

                faceOperation.saveImage(image2, name);
                System.out.println("Name= " + name);
            }

        }
        new Dao().setNumberAndNameToDB();
    }

    private void clearDir(){
        File file=new File(Const.TRANING_AND_SAVE_PATH);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png") || name.endsWith(".PNG")
                        || name.endsWith(".xml") || name.endsWith(".XML");
            }
        };
        File[] filesForErase = file.listFiles(filter);
        for(File fFE: filesForErase){
            fFE.delete();
        }

    }
}
