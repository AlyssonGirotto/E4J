package br.unioeste.jgoose.e4j.actions;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import br.unioeste.jgoose.e4j.swing.BasicGraphEditor;
import br.unioeste.jgoose.e4j.filters.DefaultFileFilter;
import br.unioeste.jgoose.e4j.shape.SubProcessShape;
import br.unioeste.jgoose.e4j.swing.EditorPalette;
import br.unioeste.jgoose.util.BPMNUtils;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.w3c.dom.Element;

@SuppressWarnings(value = "serial")
public class ImportStencilAction extends AbstractAction {

    protected String lastDir;

    /**
     * Loads and registers the shape as a new shape in mxGraphics2DCanvas and
     * adds a new entry to use that shape in the specified palette
     *
     * @param palette The palette to add the shape to.
     * @param nodeXml The raw XML of the shape
     * @param path The path to the directory the shape exists in
     * @return the string name of the shape
     */
    public static String addStencilShape(EditorPalette palette, String nodeXml, String path) {
        // Some editors place a 3 byte BOM at the start of files
        // Ensure the first char is a "<"
        int lessthanIndex = nodeXml.indexOf("<");
        nodeXml = nodeXml.substring(lessthanIndex);

        mxStencilShape newShape = new mxStencilShape(nodeXml);
        String name = newShape.getName();
        ImageIcon icon = null;
        if (path != null) {
            String iconPath = path + newShape.getIconPath();
            icon = new ImageIcon(iconPath);
        }
        // Registers the shape in the canvas shape registry
        mxGraphics2DCanvas.putShape(name, newShape);

        if (palette != null && icon != null) {
            //@TODO: magic number for w and h! get it from content of .shape?
            int w = 60;
            int h = 60;

            Element value = null;
            String type = name.toLowerCase();

            String style = "shape=" + name;
            
            switch (type) {
                case "end event":
                    value = BPMNUtils.createEndEvent();
                    break;
                case "end-cancel":
                    value = BPMNUtils.createEndEvent("", "end-cancel");
                    break;
                case "end-compensation":
                    value = BPMNUtils.createEndEvent("", "end-compensation");
                    break;
                case "end-error":
                    value = BPMNUtils.createEndEvent("", "end-error");
                    break;
                case "end-link":
                    value = BPMNUtils.createEndEvent("", "end-link");
                    break;
                case "end-message":
                    value = BPMNUtils.createEndEvent("", "end-message");
                    break;
                case "end-multiple":
                    value = BPMNUtils.createEndEvent("", "end-multiple");
                    break;
                case "end-terminate":
                    value = BPMNUtils.createEndEvent("", "end-terminate");
                    break;
                case "intermediate event":
                    value = BPMNUtils.createIntermediateEvent();
                    break;
                case "intermediate-cancel":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-cancel");
                    break;
                case "intermediate-compensation":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-compensation");
                    break;
                case "intermediate-error":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-error");
                    break;
                case "intermediate-link":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-link");
                    break;
                case "intermediate-message":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-message");
                    break;
                case "intermediate-multiple":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-multiple");
                    break;
                case "intermediate-rule":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-rule");
                    break;
                case "intermediate-timer":
                    value = BPMNUtils.createIntermediateEvent("", "intermediate-timer");
                    break;
                case "start event":
                    value = BPMNUtils.createStartEvent();
                    break;
                case "start-link":
                    value = BPMNUtils.createStartEvent("", "start-link");
                    break;
                case "start-message":
                    value = BPMNUtils.createStartEvent("", "start-message");
                    break;
                case "start-multiple":
                    value = BPMNUtils.createStartEvent("", "start-multiple");
                    break;
                case "start-rule":
                    value = BPMNUtils.createStartEvent("", "start-rule");
                    break;
                case "start-timer":
                    value = BPMNUtils.createStartEvent("", "start-timer");
                    break;
                case "text-annotation":
                    value = BPMNUtils.createTextAnnotation();
                    break;
                case "data-object":
                    value = BPMNUtils.createDataObject();
                    break;
                case "group":
                    value = BPMNUtils.createGroup();
                    break;
                case "data store":
                    value = BPMNUtils.createDataStore();
                    break;
                case "gateway parallel":
                    value = BPMNUtils.createGatewayParallel();
                    break;
                case "gateway complex":
                    value = BPMNUtils.createGatewayComplex();
                    break;
                case "gateway":
                    value = BPMNUtils.createGateway();
                    break;
                case "gateway inclusive":
                    value = BPMNUtils.createGatewayInclusive();
                    break;
                case "gateway exclusive xor data based":
                    value = BPMNUtils.createGatewayExclusive();
                    break;
                case "gateway exclusive xor event based":
                    value = BPMNUtils.createGatewayExclusiveEventBased();
                    break;
                case "sub-process":
                    value = BPMNUtils.createSubprocess();
                    style += ";" + mxConstants.STYLE_PERIMETER + "=" + mxConstants.PERIMETER_RECTANGLE
                            + mxConstants.STYLE_ROUNDED + "=1";
                    mxGraphics2DCanvas.putShape(name, new SubProcessShape(nodeXml));
                    break;
                case "task":
                    value = BPMNUtils.createTask();
                    break;
                default:
                    System.out.println("default type: " + type);
                    break;
            }

            mxGeometry geom = new mxGeometry(0, 0, w, h);
            mxCell cell = new mxCell(value, geom, style);
            cell.setVertex(true);
            palette.addTemplate(name, icon, cell);                        
        }
        return name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BasicGraphEditor editor = EditorActions.getEditor(e);
        if (editor != null) {
            String wd = (lastDir != null) ? lastDir : System.getProperty("user.dir");
            JFileChooser fc = new JFileChooser(wd);
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            // Adds file filter for Dia shape import
            fc.addChoosableFileFilter(new DefaultFileFilter(".shape", "Dia Shape " + mxResources.get("file") + " (.shape)"));
            int rc = fc.showDialog(null, mxResources.get("importStencil"));
            if (rc == JFileChooser.APPROVE_OPTION) {
                lastDir = fc.getSelectedFile().getParent();
                try {
                    if (fc.getSelectedFile().isDirectory()) {
                        EditorPalette palette = editor.insertPalette(fc.getSelectedFile().getName());
                        for (File f : fc.getSelectedFile().listFiles(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return name.toLowerCase().endsWith(".shape");
                            }
                        })) {
                            String nodeXml = mxUtils.readFile(f.getAbsolutePath());
                            addStencilShape(palette, nodeXml, f.getParent() + File.separator);
                        }
                        JComponent scrollPane = (JComponent) palette.getParent().getParent();
                        editor.getLibraryPane().setSelectedComponent(scrollPane);
                        // FIXME: Need to update the size of the palette to force a layout
                        // update. Re/in/validate of palette or parent does not work.
                        //editor.getLibraryPane().revalidate();
                    } else {
                        String nodeXml = mxUtils.readFile(fc.getSelectedFile().getAbsolutePath());
                        String name = addStencilShape(null, nodeXml, null);
                        JOptionPane.showMessageDialog(editor, mxResources.get("stencilImported", new String[]{name}));
                    }
                } catch (IOException e1) {
                }
            }
        }
    }
}
