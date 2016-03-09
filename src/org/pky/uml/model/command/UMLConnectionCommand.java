package org.pky.uml.model.command;

import org.eclipse.gef.commands.Command;
import org.pky.uml.editor.LogicMessages;
import org.pky.uml.model.LineModel;
import org.pky.uml.model.common.UMLModel;

public class UMLConnectionCommand extends Command {
	protected UMLModel oldSource;
	protected String oldSourceTerminal;
	protected UMLModel oldTarget;
	protected String oldTargetTerminal;
	protected UMLModel source;
	protected String sourceTerminal;
	protected UMLModel target;
	protected String targetTerminal;
	protected LineModel wire;

	public UMLConnectionCommand() {
		super(LogicMessages.ConnectionCommand_Label);
	}

	public boolean canExecute() {
		return true;
	}

	public void execute() {
		//20080908IJS
		//		if( !ProjectManager.getInstance().isMsgDrag()){
		//		return;
		//		}
		//		else{
		//		ProjectManager.getInstance().setMsgDrag(false);
		//		}
		if(source==null || target==null)
			return;
		try{




			if(source != null && target != null){



			}

			if (source != null) {


				

				wire.detachSource();
				wire.setSource(source);
				wire.setSourceTerminal(sourceTerminal);
				wire.attachSource();

			}
			if (target != null) {

				wire.detachTarget();
				wire.setTarget(target);
				wire.setTargetTerminal(targetTerminal);
				wire.attachTarget();



			}
			if (source == null && target == null) {


				wire.detachSource();
				wire.detachTarget();
				wire.setTarget(null);
				wire.setSource(null);
			}
			//PKY 08101380 S


		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getLabel() {
		return LogicMessages.ConnectionCommand_Description;
	}

	public UMLModel getSource() {
		return source;
	}

	public java.lang.String getSourceTerminal() {
		return sourceTerminal;
	}

	public UMLModel getTarget() {
		return target;
	}

	public String getTargetTerminal() {
		return targetTerminal;
	}

	public LineModel getWire() {
		return wire;
	}

	public void redo() {

		execute();
	}

	public void setSource(UMLModel newSource) {
		source = newSource;
	}

	public void setSourceTerminal(String newSourceTerminal) {
		sourceTerminal = newSourceTerminal;
	}

	public void setTarget(UMLModel newTarget) {
		target = newTarget;
	}

	public void setTargetTerminal(String newTargetTerminal) {
		targetTerminal = newTargetTerminal;
	}

	public void setWire(LineModel w) {
		wire = w;
		oldSource = w.getSource();
		oldTarget = w.getTarget();
		oldSourceTerminal = w.getSourceTerminal();
		oldTargetTerminal = w.getTargetTerminal();
	}

	public void undo() {
		try{

			source = wire.getSource();
			target = wire.getTarget();
			sourceTerminal = wire.getSourceTerminal();
			targetTerminal = wire.getTargetTerminal();
			wire.detachSource();
			wire.detachTarget();
			if(oldSource!=null && oldTarget!=null){

				wire.setSource(oldSource);
				wire.setTarget(oldTarget);
				wire.setSourceTerminal(oldSourceTerminal);
				wire.setTargetTerminal(oldTargetTerminal);
				wire.attachSource();
				wire.attachTarget();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
