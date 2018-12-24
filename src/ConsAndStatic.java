import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.HashMap;
import java.util.Map;

public class ConsAndStatic {
  public static   OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
  public  static final String XML_FILE_NAME="traning.xml";
  public static final String TRANING_AND_SAVE_PATH=System.getProperty("user.home")+"/Desktop/face_images/";
  public static final int FACE_RESIZE_WIDTH=140,FACE_RESIZE_HEGHT=140;
  public static final int DETECT_LIMIT=6800;
  public static Map<Integer,String> getName=new HashMap<>();

}
