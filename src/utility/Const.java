package utility;

import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.HashMap;
import java.util.Map;

public class Const {
  public static final String PATH_TO_THE_FOLDER_WITH_RAW_PICTURES=System.getProperty("user.home")+"/Desktop/Images/";
  public static final String CASCAD_XML_FILE2="C:\\Users\\Murad\\Documents\\GitHub\\FaceID\\res\\face2.xml";
  public static final String CASCAD_XML_FILE="C:\\Users\\Murad\\Documents\\GitHub\\FaceID\\res\\face.xml";
  public static final int IMAGE_COPY = 5 ;
  public static   OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
  public  static final String XML_FILE_NAME="traning.xml";
  public static final String TRANING_AND_SAVE_PATH=System.getProperty("user.home")+"/Desktop/face_images/";
  public static final int FACE_RESIZE_WIDTH=128,FACE_RESIZE_HEGHT=128;
  public static final int DETECT_LIMIT=20000;
  public static Map<Integer,String> getName=new HashMap<>();

}
