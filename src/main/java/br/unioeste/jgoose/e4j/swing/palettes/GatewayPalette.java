package br.unioeste.jgoose.e4j.swing.palettes;

import br.unioeste.jgoose.e4j.actions.ImportStencilAction;
import br.unioeste.jgoose.e4j.filters.ShapeFilenameFilter;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;

public class GatewayPalette extends AbstractPalette {

    private static final Logger CONSOLE = Logger.getLogger("console");

    public GatewayPalette(JTabbedPane libraryPane) {
        super(mxResources.get("Elements", "Gateways"), libraryPane);
                                
        File shapesFolder = new File("resources/shapes/bpmn/gateway/");
        File[] files = shapesFolder.listFiles(ShapeFilenameFilter.instance);
        
        if ((files == null) || (files.length < 1)) {
            CONSOLE.info("no shape found. " + shapesFolder.getAbsolutePath());
            return;
        }
        
        for (File f : files) {
            try {
                String nodeXml = mxUtils.readFile(f.getAbsolutePath());
                
                if (f.getName().matches("and.shape|complex.shape|gateway.shape|or.shape|"
                        + "xor-data.shape|xor-event.shape"))
                    ImportStencilAction.addStencilShape(this, nodeXml, f.getParent() + File.separator);
            } catch (IOException ex) {
                CONSOLE.fatal(ex);
            }
        }
    }
}
