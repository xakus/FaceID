package project;

import dao.Dao;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import utility.Const;
import utility.EyeCenterAndAngle;

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
        EyeDetect eyeDetect =new EyeDetect();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                name=name.toLowerCase();
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png") || name.endsWith(".PNG");
            }
        };
        File[] imageFiles = root.listFiles(filter);
        Operation faceOperation = new Operation();
        for (File image : imageFiles) {
            opencv_core.IplImage img = cvLoadImage(image.getAbsolutePath());

            String name = image.getName().split("\\.")[0];
            System.out.println("--->"+name);
            FaceDetect faceDetect = new FaceDetect();
            faceDetect.faceDetect(img, 1.3, 5, opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT);
            List<opencv_core.CvRect> cvRects = faceDetect.getCvRect();
            for (opencv_core.CvRect cvRect : cvRects) {
                double size=5.0;
                opencv_core.IplImage image1 = faceOperation.faceCut(img, cvRect.x(), cvRect.y(), cvRect.width(),cvRect.height());

                List<opencv_core.Rect> rects= eyeDetect.eyeDetect2(img);
                EyeCenterAndAngle cAA=Operation.angle(rects);
                 System.out.println(cAA.getAngle());
                opencv_core.Point2f point2f=new opencv_core.Point2f((cAA.getEye1().x()+cAA.getEye2().x())/2.0f,(cAA.getEye1().y()+cAA.getEye2().y())/2.0f);
                opencv_core.IplImage im1= faceOperation.rotate(img,point2f,cAA.getAngle());
                opencv_core.IplImage im2=faceOperation.faceCut(im1, cvRect.x(), cvRect.y(), cvRect.width(),cvRect.height());
                opencv_core.IplImage image2 = faceOperation.resizeImage(im2);
               for (int c=0;c<Const.IMAGE_COPY;c++){

                faceOperation.saveImage(image2, name+"#"+c);
                   opencv_core.IplImage mirImage= faceOperation.mirror(image2);
                   faceOperation.saveImage(mirImage, name+"#"+c+"M");
                System.out.println("Name= " + name);}
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
        Operation faceOperation = new Operation();
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
