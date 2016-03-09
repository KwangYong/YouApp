package org.pky.uml.commons.managers;

import java.util.Comparator;

import org.eclipse.draw2d.PositionConstants;
import org.pky.uml.model.common.UMLModel;

public class LayoutCompare implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {

		/**
		NORTH ºÏ 
		SOUTH ³² 
		WEST ¼­ 
		EAST µ¿ 
		 **/
		UMLModel model1 =(UMLModel)arg0;
		UMLModel model2 =(UMLModel)arg1;
		int position = model2.getLocationRect().getPosition(model1.getLocation());
		if(position==PositionConstants.NORTH){
			return -1;
		}else if(position==PositionConstants.NORTH_SOUTH){
			return 1;
		}else if(position==PositionConstants.NORTH_WEST){
			return -1;
		}else if(position==PositionConstants.SOUTH){
			return 1;
		}else if(position==PositionConstants.SOUTH_EAST){
			return 1;
		}else if(position==PositionConstants.SOUTH_WEST){
			return 1;
		}else if(position==PositionConstants.WEST){
			return -1;
		}else if(position==PositionConstants.EAST){
			return 1;
		}else if(position==PositionConstants.NORTH_EAST){
			return -1;
		}
		return 0;
	}  

}
