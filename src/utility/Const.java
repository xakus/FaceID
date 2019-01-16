package utility;

import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.HashMap;
import java.util.Map;

public class Const {
  public static final String PATH_TO_THE_FOLDER_WITH_RAW_PICTURES=System.getProperty("user.home")+"/Desktop/Images/";
  private static String cascadPath="/Users/owner/Documents/GitHub/FaceID/res/";
  public static final String FACE_CASCAD_XML_FILE2=cascadPath+"face2.xml";
  public static final String FACE_CASCAD_XML_FILE=cascadPath+"face.xml";
  public static final String EYE_CASCAD_XML_FILE2=cascadPath+"haarcascade_eye.xml";
  public static final String EYE_CASCAD_XML_FILE=cascadPath+"haarEyecascade.xml";
  public static final int IMAGE_COPY = 1 ;
  public static   OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
  public  static final String XML_FILE_NAME="traning.xml";
  public static final String TRANING_AND_SAVE_PATH=System.getProperty("user.home")+"/Desktop/face_images/";
  public static final int FACE_RESIZE_WIDTH=512,FACE_RESIZE_HEGHT=512;
  public static final int DETECT_LIMIT=100;
  public static Map<Integer,String> getName=new HashMap<>();

}
