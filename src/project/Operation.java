package project;

import org.bytedeco.javacpp.opencv_core;

import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.core.Core;
import utility.Const;
import utility.EyeCenterAndAngle;

import java.util.List;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Operation {


    int count;

    public Operation() {
        count = 0;
    }

    public IplImage faceCut(opencv_core.IplImage img, int x, int y, int w, int h) {
//        Mat mat=new Mat();
//        GaussianBlur(cvarrToMat(img),mat,new Size(3,3),1);
//         img=new IplImage(mat);

        IplImage grayImage = IplImage.create(img.width(),
                img.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(img, grayImage, CV_BGR2GRAY);



        IplImage image = getSubImage(grayImage, x, y, w, h);
       // image = equalizeHistogram(image);
        image = clahe(image);

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

    public static IplImage equalizeHistogram(IplImage img) {
        IplImage image;
        Mat src, dst = new Mat();
        src = cvarrToMat(img);
        equalizeHist(src, dst);
        image = new IplImage(dst);
        return image;
    }

    public IplImage clahe(IplImage img) {
        Mat mat1 = cvarrToMat(img);
        Mat mat2 = new Mat();
        CLAHE clahe = createCLAHE();
        clahe.setClipLimit(1);
        clahe.setTilesGridSize(new Size(5, 5));
        clahe.apply(mat1, mat2);
        IplImage img2 = new IplImage(mat2);
        return img2;

    }
    public static IplImage rotate(IplImage img,double angle){
        Mat mat1=cvarrToMat(img);
        Mat mat2=new Mat();
        Point2f point2f=new Point2f(img.width()/2,img.height()/2);

       Mat M= opencv_imgproc.getRotationMatrix2D(point2f,angle,1);
       opencv_imgproc.warpAffine(mat1,mat2,M,new Size(img.width(),img.height()),
               opencv_imgproc.INTER_LINEAR,opencv_core.BORDER_TRANSPARENT,new Scalar(0,0,0,255));
        IplImage image=new IplImage(mat2);
        return image;
    }
    public static IplImage rotate(IplImage img,double x,double y,double angle){
        Mat mat1=cvarrToMat(img);
        Mat mat2=new Mat();
        Point2f point2f=new Point2f((int)x,(int)y);

        Mat M= opencv_imgproc.getRotationMatrix2D(point2f,angle,1);
        opencv_imgproc.warpAffine(mat1,mat2,M,new Size(img.width(),img.height()),
                opencv_imgproc.INTER_LINEAR,opencv_core.BORDER_TRANSPARENT,new Scalar(0,0,0,255));
        IplImage image=new IplImage(mat2);
        return image;
    }
    public static IplImage rotate(IplImage img,Point2f poin,double angle){
        Mat mat1=cvarrToMat(img);
        Mat mat2=new Mat();


        Mat M= opencv_imgproc.getRotationMatrix2D(poin,angle,1);
        opencv_imgproc.warpAffine(mat1,mat2,M,new Size(img.width(),img.height()),
                opencv_imgproc.INTER_LINEAR,opencv_core.BORDER_TRANSPARENT,new Scalar(0,0,0,255));
        IplImage image=new IplImage(mat2);
        return image;
    }
    public IplImage mirror(IplImage img) {
        Mat mat1 = cvarrToMat(img);
        Mat mat2 = new Mat();
        opencv_core.flip(mat1, mat2, 1);
        IplImage image = new IplImage(mat2);
        return image;
    }

    public void saveImage(IplImage img, String name) {
        Const.getName.put(count, name.split("#")[0]);
        String pathAndName = Const.TRANING_AND_SAVE_PATH + count + "-" + name + ".jpg";
        imwrite(pathAndName, cvarrToMat(img));
        count++;
    }
    public static EyeCenterAndAngle angle(List<Rect> rects){
        EyeCenterAndAngle centerAndAngle=new EyeCenterAndAngle();
        if(rects.size()>1) {
            Point A=new Point((int)((rects.get(0).width()+2*rects.get(0).x())/2.0),(int)((rects.get(0).height()+2*rects.get(0).y())/2.0));
            Point B=new Point((int)((rects.get(1).width()+2*rects.get(1).x())/2.0),(int)((rects.get(1).height()+2*rects.get(1).y())/2.0));
            int a = B.x()-A.x();
            int b = B.y()-A.y();

            if(b==0){return centerAndAngle;}
            double angle=( Math.atan(a/b))*180/Math.PI;
            if(angle<0){
                angle=-1*(angle+90);
            }else if(angle>0){
                angle=90-angle;
            }
            centerAndAngle.setAngle(angle);
            centerAndAngle.setEye1(A);
            centerAndAngle.setEye2(B);
            return centerAndAngle;
        }else {return centerAndAngle;}
    }

}
