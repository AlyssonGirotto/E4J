package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.e4j.actions.ImportStencilAction;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import java.io.File;
import java.io.IOException;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;

public class EventPalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public EventPalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Events"), libraryPane);
        
        File shapesFolder = new File("resources/shapes/bpmn/event/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        
        if ((files == null) || (files.length < 1)) {
            CONSOLE.info("no shape found. " + shapesFolder.getAbsolutePath());
            return;
        }
        
        for (File f : files) {
            try {
                String nodeXml = mxUtils.readFile(f.getAbsolutePath());
                
                if (f.getName().matches("3end.shape|2intermediate.shape|1start.shape|"
                        + "end-compensation.shape|end-cancel.shape|end-error.shape|"
                        + "end-link.shape|end-message.shape|end-multiple.shape|end-terminate.shape|"
                        + "start-link.shape|start-message.shape|start-multiple.shape|start-rule.shape|start-timer.shape|"
                        + "intermediate-cancel.shape|intermediate-compensation.shape|intermediate-error.shape||intermediate-timer.shape|" 
                        + "intermediate-link.shape|intermediate-message.shape|intermediate-multiple.shape|intermediate-rule.shape"))
                    ImportStencilAction.addStencilShape(this, nodeXml, f.getParent() + File.separator);
            } catch (IOException ex) {
                CONSOLE.fatal(ex);
            }
        }
    }
}
