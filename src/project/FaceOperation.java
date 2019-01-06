package project;

import org.bytedeco.javacpp.opencv_core;
import utility.Const;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FaceOperation {



    int count;
    public FaceOperation() {
        count=0;
    }

    public IplImage faceCut(opencv_core.IplImage img, int x, int y, int w, int h) {
        Mat mat=new Mat();
        GaussianBlur(cvarrToMat(img),mat,new Size(5,5),10);
        IplImage ima=new IplImage(mat);

        IplImage grayImage = IplImage.create(ima.width(),
                ima.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(ima, grayImage, CV_BGR2GRAY);


        IplImage image = getSubImage(grayImage, x, y, w, h);

       image=equalizeHistogram(image);
      return image;
    }

    public IplImage getSubImage(opencv_core.IplImage img, int x, int y, int w, int h) {
        opencv_core.IplImage iplImage = opencv_core.IplImage.create(w, h, img.depth(), img.nChannels());
        cvSetImageROI(img, cvRect(x, y, w, h));
        cvCopy(img, iplImage);
        cvResetImageROI(img);
        return iplImage;
    }

    public IplImage resizeImage(IplImage img) {
        IplImage resizeImage = IplImage.create(Const.FACE_RESIZE_WIDTH, Const.FACE_RESIZE_HEGHT, img.depth(), img.nChannels());
        cvResize(img, resizeImage);
        return resizeImage;
    }
    public IplImage equalizeHistogram(IplImage img){
        IplImage image;
        Mat src, dst = new Mat();
        src=cvarrToMat(img);
        equalizeHist(src,dst);
        image= new IplImage(dst);
        return image;
    }

    public  void saveImage(IplImage img, String name){
        Const.getName.put(count,name.split("#")[0]);
        String pathAndName= Const.TRANING_AND_SAVE_PATH+count+"-"+name+".jpg";
        imwrite(pathAndName,cvarrToMat(img));
        count++;
    }


}
