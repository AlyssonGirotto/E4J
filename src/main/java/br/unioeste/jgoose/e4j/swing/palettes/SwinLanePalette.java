package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.e4j.actions.ImporStencilAction;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import br.unioeste.jgoose.util.BPMNUtils;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class SwinLanePalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public SwinLanePalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Swimlanes"), libraryPane);    
        
        Element pool = BPMNUtils.createPool();
        this.addTemplate("Pool", 
                new ImageIcon("resources/shapes/bpmn/swimlane/pool.png"), 
                "shape=swimlane;connectable=0;horizontal=false", 
                380, 180, pool);
        
        Element lane = BPMNUtils.createLane();
        this.addTemplate("Lane", 
                new ImageIcon("resources/shapes/bpmn/swimlane/lane.png"), 
                "shape=swimlane;autosize=1;connectable=false;horizontal=false", 
                380, 180, lane);
    }
}
