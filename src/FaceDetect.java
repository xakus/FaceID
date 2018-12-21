import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_STAGE_MAX;

public class FaceDetect {
    opencv_objdetect.CvHaarClassifierCascade cascade;
    List<opencv_core.CvRect> cvRect;
    public FaceDetect() {
        String f = "/Users/owner/Documents/GitHub/FaceID/res/face.xml";
        cascade = new opencv_objdetect.CvHaarClassifierCascade(cvLoad(f));

    }

    public opencv_core.CvSeq faceDetect(opencv_core.IplImage img, double scale_factor, int min_neighbors, int CV_HAAR_FLAG) {
        opencv_core.CvMemStorage memStorage = opencv_core.CvMemStorage.create();
        opencv_core.CvSeq face = cvHaarDetectObjects(img, cascade, memStorage, scale_factor, min_neighbors, CV_HAAR_FLAG);
        int total = face.total();
        if (total > 0) {
            cvRect=new LinkedList<>();
            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(face, i));
                cvRect.add(rect);
            }
        }
        return face;
    }

    public void faceRectangleAndText(opencv_core.IplImage img, opencv_core.CvSeq cvSeq, String text) {
        int total = cvSeq.total();
        if (total > 0) {

            System.out.println(total);
            for (int i = 0; i < total; i++) {
                opencv_core.CvRect rect = new opencv_core.CvRect(cvGetSeqElem(cvSeq, i));

                int x = rect.x(), y = rect.y(), w = rect.width(), h = rect.height();
                rectangle(cvarrToMat(img), new opencv_core.Rect(x, y, w, h), new opencv_core.Scalar(0, 255, 128, 0), 1, 0, 0);
                putText(cvarrToMat(img), text, new opencv_core.Point(x, (y + h + 15)), 1, 1.0, new opencv_core.Scalar(0, 255, 50, 0));
            }
        }
    }
    public List<opencv_core.CvRect> getCvRect(){
        return cvRect;
    }
}
