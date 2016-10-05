package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.e4j.actions.ImportStencilAction;
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

public class ActivityPalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public ActivityPalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Activities"), libraryPane);
        
        File shapesFolder = new File("resources/shapes/bpmn/activity/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        
        if ((files == null) || (files.length < 1)) {
            CONSOLE.info("no shape found. " + shapesFolder.getAbsolutePath());
            return;
        }
        
        for (File f : files) {
            try {
                String nodeXml = mxUtils.readFile(f.getAbsolutePath());
                
                if (f.getName().matches("task.shape|sub-process.shape"))
                    ImportStencilAction.addStencilShape(this, nodeXml, f.getParent() + File.separator);
            } catch (IOException ex) {
                CONSOLE.fatal(ex);
            }
        }
        /*
        Element subProcess = BPMNUtils.createSubprocess();
        this.addTemplate("Sub-Process", 
                new ImageIcon("resources/shapes/bpmn/activity/sub-process.png"), 
                "shape=swimlane;sub-process;dashed", 
                80, 80, subProcess);
        */
    }
}
