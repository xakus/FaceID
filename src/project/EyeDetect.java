package project;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;
import utility.Const;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_MAGIC_VAL;

public class EyeDetect {
    opencv_objdetect.CascadeClassifier cascadef;
    opencv_objdetect.CvHaarClassifierCascade cascade;
    List<opencv_core.CvRect> cvRect;
    FaceRecognizingAndTraning predict;

    public EyeDetect() {
        String f = Const.EYE_CASCAD_XML_FILE2;
        String f2 = Const.EYE_CASCAD_XML_FILE;
        cascadef = new opencv_objdetect.CascadeClassifier(f2);
        //cascade = new opencv_objdetect.CvHaarClassifierCascade(cvLoad(f));
        predict = new FaceRecognizingAndTraning();

    }

    public opencv_core.CvSeq eyeDetect(opencv_core.IplImage img, double scale_factor, int min_neighbors, int CV_HAAR_FLAG) {
        opencv_core.CvMemStorage memStorage = opencv_core.CvMemStorage.create();

        opencv_core.CvSeq face = cvHaarDetectObjects(img, cascade, memStorage, scale_factor, min_neighbors, CV_HAAR_FLAG);

        int total = face.total();
        cvRect = new LinkedList<>();
        if (total > 0) {

            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(face, i));
                cvRect.add(rect);
            }
        }
        return face;
    }

    public List<opencv_core.Rect> eyeDetect2(opencv_core.IplImage img) {

        opencv_core.RectVector rects = new opencv_core.RectVector();
        opencv_core.Size minSize=new opencv_core.Size(10,10);
        opencv_core.Size maxSize=new opencv_core.Size(300,300);

        cascadef.detectMultiScale(cvarrToMat(img), rects, 1.3, 6, CV_HAAR_MAGIC_VAL,minSize,maxSize );
        List<opencv_core.Rect> cvRect = new LinkedList<>();

        if (rects.size() > 0) {

            for (int i = 0; i < rects.size(); i++) {

                cvRect.add(rects.get(i));
            }
        }
        return cvRect;
    }

    public void eyeRectangle(opencv_core.IplImage img, opencv_core.CvSeq cvSeq) {
        int total = cvSeq.total();
        if (total > 0) {

            Operation operation = new Operation();
            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(cvSeq, i));
                int x = rect.x(), y = rect.y(), w = rect.width(), h = rect.height();
                opencv_core.IplImage image = operation.faceCut(img, x, y, w, h);
                opencv_core.IplImage image1 = operation.resizeImage(image);
                FacePredict pr = predict.facePredict(image1);
                rectangle(cvarrToMat(img), new opencv_core.Rect(x, y, w, h), new opencv_core.Scalar(0, 255, 128, 0), 1, 0, 0);

            }
        }
    }

    public void eyeRectangle(opencv_core.IplImage img, List<opencv_core.Rect> rects) {
        int total = rects.size();
        if (total > 0) {

            Operation operation = new Operation();
            for (int i = 0; i < total; i++) {
                opencv_core.Rect rect = rects.get(i);
                int x = rect.x(), y = rect.y(), w = rect.width(), h = rect.height();
                opencv_core.IplImage image = operation.faceCut(img, x, y, w, h);
                opencv_core.IplImage image1 = operation.resizeImage(image);
                FacePredict pr = predict.facePredict(image1);
                rectangle(cvarrToMat(img), rect, new opencv_core.Scalar(0, 100, 255, 0), 1, 0, 0);
                Point A=new Point((2*rect.x()+rect.width())/2,(2*rect.y()+rect.height())/2);
                opencv_imgproc.circle(cvarrToMat(img),A,10,new opencv_core.Scalar(0, 0, 255,0));
            }
        }
    }

    public List<opencv_core.CvRect> getCvRect() {
        return cvRect;
    }
}
