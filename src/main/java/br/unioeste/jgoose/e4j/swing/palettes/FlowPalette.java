package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.util.BPMNUtils;
import com.mxgraph.util.mxResources;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class FlowPalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public FlowPalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Flows"), libraryPane);      
        
        Element sequenceFlow = BPMNUtils.createSequenceFlow();
        this.addEdgeTemplate("Sequence", new ImageIcon("resources/shapes/bpmn/flow/sequence.png"), "straight;edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical", 80, 80, sequenceFlow);

        Element associationFlow = BPMNUtils.createAssociationFlow();
        this.addEdgeTemplate("Association", new ImageIcon("resources/shapes/bpmn/flow/association.png"), "vertical;endArrow=open;dashed=1", 80, 80, associationFlow);
        
        Element messageFlow = BPMNUtils.createMessageFlow();
        this.addEdgeTemplate("Message", new ImageIcon("resources/shapes/bpmn/flow/message.png"), "straight;edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical;startArrow=oval;dashed=1", 80, 80, messageFlow);        

        Object item = this.getComponent(0);
        this.setSelectionEntry((JLabel) item, null);       
    }
}
