/*    */ package br.unioeste.jgoose.e4j.actions;

/*    */
 /*    */ import com.mxgraph.model.mxCell;
/*    */ import com.mxgraph.model.mxGeometry;
/*    */ import com.mxgraph.model.mxIGraphModel;
/*    */ import com.mxgraph.swing.util.mxGraphActions;
/*    */ import com.mxgraph.util.mxPoint;
/*    */ import com.mxgraph.view.mxGraph;
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;

/*    */
 /*    */
 /*    */
 /*    */
public class ExpandOrCollapseSubprocess
        extends AbstractAction {

    final int PORT_DIAMETER = 80;

    final int PORT_RADIUS = 40;

    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphActions.getGraph(e);
        if ((graph != null) && (!graph.isSelectionEmpty())) {
            mxCell cell = (mxCell) graph.getSelectionCell();
            
            boolean isSubprocess = cell.getAttribute("type").matches("subprocess");
            //boolean hasChild = cell.getChildCount() > 0;

            if ((cell.isVertex()) && (isSubprocess)) {
                mxIGraphModel model = graph.getModel();

                model.beginUpdate();
                              
                mxGeometry geo = new mxGeometry(0.0D, 0.5D, 110.0D, 110.0D);
                geo.setOffset(new mxPoint(-20.0D, -20.0D));
                geo.setRelative(true);
                
                String style = "shape=subprocess";
                mxCell port = new mxCell(cell.getAttribute("label"), geo, style);
                port.setVertex(true);
                port.setConnectable(false);
                graph.addCell(port, cell);
                cell.setStyle(cell.getStyle() + ";noLabel=1;rounded");
                graph.cellsOrdered(new Object[]{cell}, true);
                model.endUpdate();
            }
        }
    }
}
