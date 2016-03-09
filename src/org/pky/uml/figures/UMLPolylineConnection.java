package org.pky.uml.figures;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.pky.uml.anchor.UMLConnectionLocator;
import org.pky.uml.editparts.LineEditPart;
import org.pky.uml.figures.common.UMLElementFigure;

public class UMLPolylineConnection extends PolylineConnection {
    private LinePanelFigure middleFigure = new LinePanelFigure();
    private LinePanelFigure targetFigure = new LinePanelFigure();
    private LinePanelFigure sourceFigure = new LinePanelFigure();
    private UMLElementFigure middleTop = new UMLElementFigure();
    private UMLElementFigure middleBottom = new UMLElementFigure();
    private UMLElementFigure sourceTop = new UMLElementFigure();
    private UMLElementFigure sourceBottom = new UMLElementFigure();
    private UMLElementFigure targetTop = new UMLElementFigure();
    private LineEditPart lineEditPart = null;//PKY 08101566 S
    //	private CollaborationFigure collaborationFigure = new CollaborationFigure();
    private UMLElementFigure targetBottom = new UMLElementFigure(PositionConstants.RIGHT);

    public UMLPolylineConnection() {
        middleFigure.add(middleTop);
        middleFigure.add(middleBottom);
        targetFigure.add(targetTop);
        targetFigure.add(targetBottom);
        sourceFigure.add(targetTop);
        sourceFigure.add(sourceBottom);
        //		targetBottom.set
        this.add(middleFigure, new UMLConnectionLocator(this, ConnectionLocator.MIDDLE));
        this.add(targetFigure, new UMLConnectionLocator(this, ConnectionLocator.TARGET));
        this.add(sourceFigure, new UMLConnectionLocator(this, ConnectionLocator.SOURCE));
    }

    public void setMiddle(String top, String bottom) {
        middleTop.setText(top);
        middleBottom.setText(bottom);
    }

    public void setTarget(String top, String bottom) {
        targetTop.setText(top);
        targetBottom.setText(bottom);
    }

    public void setSource(String top, String bottom) {
        sourceTop.setText(top);
        sourceBottom.setText(bottom);
        //		this.get
    }

	public LineEditPart getLineEditPart() {
		return lineEditPart;
	}

	public void setLineEditPart(LineEditPart lineEditPart) {
		this.lineEditPart = lineEditPart;
	}
}
