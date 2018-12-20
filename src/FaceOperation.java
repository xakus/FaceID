import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

public class FaceOperation {

    int faceW = 10;
    int faceH = 10;
    String faceSavePath;

    public FaceOperation(String faceSavePath, int saveImgWidth, int saveImgHeight) {
        this.faceSavePath = faceSavePath;
        faceW = saveImgWidth;
        faceH = saveImgHeight;

    }

    public void faceCutAndSave(opencv_core.IplImage img,String name, int x, int y, int w, int h) {
        IplImage image = getSubImage(img, x, y, w, h);

        resizeAndSaveImage(image, faceSavePath,name);
    }

    private opencv_core.IplImage getSubImage(opencv_core.IplImage img, int x, int y, int w, int h) {
        opencv_core.IplImage iplImage = opencv_core.IplImage.create(w, h, img.depth(), img.nChannels());
        cvSetImageROI(img, cvRect(x, y, w, h));
        cvCopy(img, iplImage);
        cvResetImageROI(img);
        return iplImage;
    }

    private void resizeAndSaveImage(IplImage img, String path,String name) {
        IplImage resizeImage = IplImage.create(faceW, faceH, img.depth(), img.nChannels());
        cvResize(img, resizeImage);
        cvSaveImage(path+name+".jpg", resizeImage);

    }

}
