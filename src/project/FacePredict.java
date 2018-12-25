package project;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;

public class FacePredict {
    IntPointer label;
    DoublePointer confidence;

    public IntPointer getLabel() {
        return label;
    }

    public void setLabel(IntPointer label) {
        this.label = label;
    }

    public DoublePointer getConfidence() {
        return confidence;
    }

    public void setConfidence(DoublePointer confidence) {
        this.confidence = confidence;
    }
}
