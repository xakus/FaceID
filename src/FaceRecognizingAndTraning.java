import org.bytedeco.javacpp.*;
import org.opencv.face.PredictCollector;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class FaceRecognizingAndTraning {
    private String traningAndSavePath;
    opencv_face.FaceRecognizer faceRecognizer;

    public FaceRecognizingAndTraning() {
        this.traningAndSavePath = ConsAndStatic.TRANING_AND_SAVE_PATH;
        faceRecognizer = opencv_face.EigenFaceRecognizer.create();
        try {
            File xml = new File(traningAndSavePath+ConsAndStatic.XML_FILE_NAME);
            faceRecognizer.read(xml.getAbsolutePath());
        }catch (Exception e){
            System.out.println("XML DONT FIND");
        }

    }

    public void traning() {
        File root = new File(traningAndSavePath);
        FilenameFilter imgFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".JPG") || name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png")|| name.endsWith(".PNG");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);
        opencv_core.MatVector images = new opencv_core.MatVector(imageFiles.length);
        opencv_core.Mat labels = new opencv_core.Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int name = Integer.parseInt(image.getName().split("\\-")[0]);
            System.out.println("Traning Name=" + name);
            images.put(name, img);
            labelBuffer.put(name, name);

        }

        faceRecognizer.train(images, labels);
        faceRecognizer.save(traningAndSavePath + ConsAndStatic.XML_FILE_NAME);
    }

    public FacePredict facePredict(IplImage img) {
        FacePredict predict = new FacePredict();
        opencv_face.PredictCollector predictCollector;
        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        faceRecognizer.predict(cvarrToMat(img), label, confidence);
        predict.setLabel(label);
        predict.setConfidence(confidence);
        return predict;
    }
}
