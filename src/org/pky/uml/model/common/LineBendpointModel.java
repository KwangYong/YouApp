package org.pky.uml.model.common;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class LineBendpointModel implements java.io.Serializable, Bendpoint {
    private float weight = 0.5f;
    private Dimension d1, d2;

    public LineBendpointModel() {
    	System.out.print("");
    }

    public Dimension getFirstRelativeDimension() {
        return d1;
    }

    public Point getLocation() {
        return null;
    }

    public Dimension getSecondRelativeDimension() {
        return d2;
    }

    public float getWeight() {
        return weight;
    }

    public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
        d1 = dim1;
        d2 = dim2;
    }

    public void setWeight(float w) {
        weight = w;
    }
    
    public String writeModel(){
    	return weight+","+d1.width+","+d1.height+","+d2.width+","+d2.height;
    }

    public Object clone() {
        LineBendpointModel clone = new LineBendpointModel();
        clone.setRelativeDimensions(this.getFirstRelativeDimension().getCopy(), this.getSecondRelativeDimension().getCopy());
        clone.setWeight(this.getWeight());
        return clone;
    }
}
