package br.unioeste.jgoose.e4j.swing;

import br.unioeste.jgoose.e4j.CustomGraph;
import br.unioeste.jgoose.e4j.CustomGraphBPMN;
import br.unioeste.jgoose.e4j.swing.listeners.SelectedEdgeChangeEventListener;
import br.unioeste.jgoose.e4j.swing.palettes.ActivityPalette;
import br.unioeste.jgoose.e4j.swing.palettes.ArtifactPalette;
import br.unioeste.jgoose.e4j.swing.palettes.EventPalette;
import br.unioeste.jgoose.e4j.swing.palettes.FlowPalette;
import br.unioeste.jgoose.e4j.swing.palettes.GatewayPalette;
import br.unioeste.jgoose.e4j.swing.palettes.SwinLanePalette;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

public class BasicBPMNEditor extends BasicGraphEditor {

    private static final long serialVersionUID = -4601740824088314699L;
    private static final Logger CONSOLE = Logger.getLogger("console");

    public static final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt_BR"));
    public static URL url = null;
    private static mxGraph graph;
    
    public BasicBPMNEditor(JFrame frame) {
        this("JGOOSE - BPMN Editor", new CustomGraphComponent(new CustomGraphBPMN(), true));
        frame.setTitle("JGOOSE - Editor BPMN");
        super.setFrame(frame);
    }

    public static mxGraph getMxGraph(){ return graph; }
    
    public BasicBPMNEditor(String appTitle, mxGraphComponent component) {
        super(appTitle, component);
        this.graphComponent.setEnterStopsCellEditing(true);
        mxGraph graph = this.graphComponent.getGraph();
        
        
        // Creates the shapes palette
        EditorPalette eventPalette = new EventPalette(this.libraryPane);
        EditorPalette artifactPalette = new ArtifactPalette(this.libraryPane);
        EditorPalette gatewayPalette = new GatewayPalette(this.libraryPane);
        EditorPalette activityPalette = new ActivityPalette(this.libraryPane);
        EditorPalette flowPalette = new FlowPalette(this.libraryPane);
        EditorPalette swimlanePalette = new SwinLanePalette(this.libraryPane);
                
        eventPalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));        
        artifactPalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));
        gatewayPalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));
        activityPalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));
        flowPalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));
        swimlanePalette.addListener("select", (mxEventSource.mxIEventListener) new SelectedEdgeChangeEventListener(graph));
               
        Object item = eventPalette.getComponent(0);
        eventPalette.setSelectionEntry((JLabel) item, null);
    }

    public void exit() {
        if (this.frame != null) {
            ((EditorJFrame) this.frame).exit();
        } else {
            CONSOLE.debug("editorJFrame is null");
        }
    }
}