package project;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import utility.ConsAndStatic;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

public class FaceDetect {
    opencv_objdetect.CascadeClassifier cascadef;
    opencv_objdetect.CvHaarClassifierCascade cascade;
    List<opencv_core.CvRect> cvRect;
    FaceRecognizingAndTraning predict;
    public FaceDetect() {
        String f = ConsAndStatic.CASCAD_XML_FILE;
        String f2= ConsAndStatic.CASCAD_XML_FILE2;
        cascadef = new opencv_objdetect.CascadeClassifier(f2);
        cascade = new opencv_objdetect.CvHaarClassifierCascade(cvLoad(f));
        predict=new FaceRecognizingAndTraning();

    }

    public opencv_core.CvSeq faceDetect(opencv_core.IplImage img, double scale_factor, int min_neighbors, int CV_HAAR_FLAG) {
        opencv_core.CvMemStorage memStorage = opencv_core.CvMemStorage.create();

        opencv_core.CvSeq face = cvHaarDetectObjects(img, cascade, memStorage, scale_factor, min_neighbors, CV_HAAR_FLAG);
        int total = face.total();
        cvRect=new LinkedList<>();
        if (total > 0) {

            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(face, i));
                cvRect.add(rect);
            }
        }
        return face;
    }

    public List<opencv_core.Rect> faceDetect2(opencv_core.IplImage img) {

        opencv_core.RectVector rects=new opencv_core.RectVector();
        cascadef.detectMultiScale(cvarrToMat(img),rects);
        List<opencv_core.Rect> cvRect=new LinkedList<>();

        if (rects.size() > 0) {

            for (int i = 0; i < rects.size(); i++) {

                cvRect.add(rects.get(i));
                rectangle(cvarrToMat(img), rects.get(i), new opencv_core.Scalar(0, 0,255 , 0), 1, 0, 0);
            }
        }
        return cvRect;
    }

    public void faceRectangleAndText(opencv_core.IplImage img, opencv_core.CvSeq cvSeq) {
        int total = cvSeq.total();
        if (total > 0) {
           String text="???????";

            FaceOperation operation=new FaceOperation();
            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(cvSeq, i));
                int x = rect.x(), y = rect.y(), w = rect.width(), h = rect.height();
                opencv_core.IplImage image =operation.faceCut(img,x,y,w,h);
                opencv_core.IplImage image1=operation.resizeImage(image);
                FacePredict pr=predict.facePredict(image1);
                if(pr.getConfidence().get()< ConsAndStatic.DETECT_LIMIT) {
                    text = "N=" +ConsAndStatic.getName.get( pr.getLabel().get()) + " P=" + pr.getConfidence().get();
                    System.out.println(text);
                }else{
                    text = "N=" + -1 + " P=" + pr.getConfidence().get();
                }


                rectangle(cvarrToMat(img), new opencv_core.Rect(x, y, w, h), new opencv_core.Scalar(0, 255, 128, 0), 1, 0, 0);
                putText(cvarrToMat(img), text, new opencv_core.Point(x, (y + h + 15)), 1, 1.0, new opencv_core.Scalar(0, 0, 255, 0));
            }
        }
    }
    public void faceRectangleAndText2(opencv_core.IplImage img, List<opencv_core.Rect> rects) {
        int total = rects.size();
        if (total > 0) {
            String text="???????";

            FaceOperation operation=new FaceOperation();
            for (int i = 0; i < total; i++) {
                opencv_core.Rect rect = rects.get(i);
                int x = rect.x(), y = rect.y(), w = rect.width(), h = rect.height();
                opencv_core.IplImage image =operation.faceCut(img,x,y,w,h);
                opencv_core.IplImage image1=operation.resizeImage(image);
                FacePredict pr=predict.facePredict(image1);
                if(pr.getConfidence().get()< ConsAndStatic.DETECT_LIMIT) {
                    text = "N=" +ConsAndStatic.getName.get( pr.getLabel().get()) + " P=" + pr.getConfidence().get();
                    System.out.println(text);
                }else{
                    text = "N=" + -1 + " P=" + pr.getConfidence().get();
                }


                rectangle(cvarrToMat(img), rect, new opencv_core.Scalar(0, 100, 255, 0), 1, 0, 0);
                putText(cvarrToMat(img), text, new opencv_core.Point(x, (y + h + 15)), 1, 1.0, new opencv_core.Scalar(0, 10, 255, 0));
            }
        }
    }
    public List<opencv_core.CvRect> getCvRect(){
        return cvRect;
    }
}