package Frame;

import org.bytedeco.javacpp.FlyCapture2;

import javax.swing.*;
import java.awt.*;

public class View {
    private JPanel image;
    public void setImage(Image img){
        Graphics gr= image.getGraphics();
        gr.drawImage(img,0,0,img.getWidth(null),img.getHeight(null),null);
    }
}
