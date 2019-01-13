package utility;

import org.bytedeco.javacpp.opencv_core;

public class EyeCenterAndAngle {
    private double angle=0.0;
    private opencv_core.Point center=new opencv_core.Point(0,0);
    private opencv_core.Point eye1=new opencv_core.Point(0,0);
    private opencv_core.Point eye2=new opencv_core.Point(0,0);

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public opencv_core.Point getCenter() {
        return center;
    }

    public void setCenter(opencv_core.Point center) {
        this.center = center;
    }

    public opencv_core.Point getEye1() {
        return eye1;
    }

    public void setEye1(opencv_core.Point eye1) {
        this.eye1 = eye1;
    }

    public opencv_core.Point getEye2() {
        return eye2;
    }

    public void setEye2(opencv_core.Point eye2) {
        this.eye2 = eye2;
    }
}
