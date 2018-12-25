package project;

import org.bytedeco.javacpp.opencv_core;
import utility.ConsAndStatic;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

public class FaceOperation {



    int count;
    public FaceOperation() {
        count=0;
    }

    public IplImage faceCut(opencv_core.IplImage img, int x, int y, int w, int h) {
        IplImage grayImage = IplImage.create(img.width(),
                img.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(img, grayImage, CV_BGR2GRAY);

        IplImage image = getSubImage(grayImage, x, y, w, h);


      return image;
    }

    public opencv_core.IplImage getSubImage(opencv_core.IplImage img, int x, int y, int w, int h) {



        opencv_core.IplImage iplImage = opencv_core.IplImage.create(w, h, img.depth(), img.nChannels());
        cvSetImageROI(img, cvRect(x, y, w, h));
        cvCopy(img, iplImage);
        cvResetImageROI(img);
        return iplImage;
    }

    public IplImage resizeImage(IplImage img) {
        IplImage resizeImage = IplImage.create(ConsAndStatic.FACE_RESIZE_WIDTH, ConsAndStatic.FACE_RESIZE_HEGHT, img.depth(), img.nChannels());
        cvResize(img, resizeImage);
        return resizeImage;
    }
    public  void saveImage(IplImage img, String name){
        ConsAndStatic.getName.put(count,name.split("#")[0]);
        String pathAndName= ConsAndStatic.TRANING_AND_SAVE_PATH+count+"-"+name+".jpg";
        imwrite(pathAndName,cvarrToMat(img));
        count++;
    }

}
