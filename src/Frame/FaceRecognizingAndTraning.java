package Frame;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.nio.IntBuffer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class FaceRecognizingAndTraning {
    private String traningAndSavePath;
    opencv_face.FaceRecognizer faceRecognizer;
    public FaceRecognizingAndTraning(String traningAndSavePath) {
        this.traningAndSavePath = traningAndSavePath;
        faceRecognizer= opencv_face.FisherFaceRecognizer.create();
    }

    public void traning() {
        File root = new File(traningAndSavePath);
        FilenameFilter imgFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);
        opencv_core.MatVector images = new opencv_core.MatVector(imageFiles.length);
        opencv_core.Mat labels =new opencv_core.Mat(imageFiles.length,1,CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();
        int count=0;
        for(File image: imageFiles){
            Mat img = imread(image.getAbsolutePath(),CV_LOAD_IMAGE_GRAYSCALE);
            int name=Integer.parseInt(image.getName().split("\\-")[0]);
            System.out.println("Traning Name="+name);
            images.put(name,img);
            labelBuffer.put(name,name);
            count++;
        }

        faceRecognizer.train(images,labels);
        faceRecognizer.save(traningAndSavePath+"traning.xml");
    }
    public IntPointer predict(IplImage img){
        IntPointer label=new IntPointer(1);
        DoublePointer confidence=new DoublePointer(1);
        faceRecognizer.predict(cvarrToMat(img),label,confidence);
        return label;
    }
}
