/*    */ package br.unioeste.jgoose.e4j.actions;

/*    */
import br.unioeste.jgoose.util.BPMNUtils;
/*    */ import com.mxgraph.model.mxCell;
/*    */ import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
/*    */ import com.mxgraph.model.mxIGraphModel;
/*    */ import com.mxgraph.swing.util.mxGraphActions;
/*    */ import com.mxgraph.util.mxPoint;
/*    */ import com.mxgraph.view.mxGraph;
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.w3c.dom.Element;

/*    */
 /*    */
 /*    */
 /*    */
 /*    */ public class AddLane
        /*    */ extends AbstractAction /*    */ {

    /* 18 */   //final int PORT_DIAMETER = 80;
/*    */
 /* 20 */   //final int PORT_RADIUS = 40;
/*    */
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphActions.getGraph(e);

        if ((graph != null) && (!graph.isSelectionEmpty())) {
            mxCell cell = (mxCell) graph.getSelectionCell();
            boolean isPool = cell.getAttribute("type").matches("pool");

            System.out.println("Add lane isPool: " + isPool);
            if ((cell.isVertex()) && (isPool)) {
                mxIGraphModel model = graph.getModel();
                model.beginUpdate();

                boolean hasChildLane = false;
                System.out.println("count " + cell.getChildCount());

                mxCell child;

                // check if exists lanes
                for (int i = 0; i < cell.getChildCount(); i++) {
                    try {
                        Object object = cell.getChildAt(i);
                        System.out.println("object.getClass() " + object.getClass());
                        child = (mxCell) cell.getChildAt(i);
                        if (child.getAttribute("type") != null && child.getAttribute("type").equals("lane")) {
                            hasChildLane = true;
                            break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                // Add new lane
                System.out.println("has child : " + hasChildLane);

                mxGeometry geo;
                double heightPool = cell.getGeometry().getHeight();

                if (!hasChildLane) {
                    geo = new mxGeometry(0.0D, 0.0D, (cell.getGeometry().getWidth() - 40), heightPool);
                } else {
                    // update height pool          
                    cell.getGeometry().setHeight(cell.getGeometry().getHeight() + 230);
                    geo = new mxGeometry(0.0D, heightPool, (cell.getGeometry().getWidth() - 40), 230);
                }

                Element laneElement = BPMNUtils.createLane();
                String style = "shape=swimlane;horizontal=false";
                
                mxCell lane = new mxCell(laneElement, geo, style);

                lane.setVertex(true);
                lane.setConnectable(false);

                graph.addCell(lane, cell);

                graph.cellsOrdered(new Object[]{cell}, true);

                model.endUpdate();
            }
        }
    }
}
