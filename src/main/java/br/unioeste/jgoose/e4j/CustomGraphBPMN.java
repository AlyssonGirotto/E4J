package br.unioeste.jgoose.e4j;

import static br.unioeste.jgoose.e4j.swing.BasicIStarEditor.numberFormat;
import br.unioeste.jgoose.util.BPMNUtils;
import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import com.mxgraph.view.mxStyleRegistry;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 * A graph that creates new edges from a given template edge.
 *
 * @author Alysson.Girotto
 */
public class CustomGraphBPMN extends mxGraph {

    private static final Logger LOG = Logger.getLogger("console");
    /**
     * Holds the edge to be used as a template for inserting new edges.
     */
    protected Object edgeTemplate;

    public CustomGraphBPMN() {
        super();

        setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical;");

        // set default edge (sequence)
        Element sequenceFlow = BPMNUtils.createSequenceFlow();
        mxGeometry geom = new mxGeometry(0, 0, 80, 80);
        Object cell = new mxCell(sequenceFlow, geom, "straight;edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");

        ((mxCell) cell).setEdge(true);
        setEdgeTemplate(cell);               
    }

    /**
     * Sets the edge template to be used to inserting edges.
     *
     * @param template
     */
    public void setEdgeTemplate(Object template) {
        edgeTemplate = template;
    }

    /**
     * Overrides the method to use the currently selected edge template for new
     * edges.
     *
     * @param parent Cell that specifies the parent of the new edge.
     * @param id Optional string that defines the Id of the new edge.
     * @param value Object to be used as the user object.
     * @param source Cell that defines the source of the edge.
     * @param target Cell that defines the target of the edge.
     * @param style Optional string that defines the cell style.
     * @return Returns the new edge to be inserted.
     */
    @Override
    public Object createEdge(Object parent, String id, Object value,
            Object source, Object target, String style) {

        if (edgeTemplate != null) {
            mxCell edge = (mxCell) cloneCells(new Object[]{edgeTemplate})[0];

            if (edge == null) {
                return null;
            }

            edge.setId(id);
            return edge;
        }
        return super.createEdge(parent, id, value, source, target, style);
    }

    @Override
    public boolean isValidDropTarget(Object cell, Object[] cells) {                
        return super.isValidDropTarget(cell, cells);
    }
    
    
//    @Override
//    public boolean isValidDropTarget(Object cell, Object[] cells) {
//        mxCell target = (mxCell)cell;
//        mxCell source = (mxCell)cells[0];        
//        if(!target.isCollapsed() && source.getAttribute("type").matches("position|role|actor|agent")){
//            return false;
//        }
//        return super.isValidDropTarget(cell, cells);
//    }
    /**
     *
     * @param edge
     * @param source
     * @param target
     * @return null == valid. "" == not valid.
     */
    @Override
    public String getEdgeValidationError(Object edge, Object source, Object target) {

        String result = super.getEdgeValidationError(edge, source, target);

//        boolean result = super.isEdgeValid(edge, source, target);
        //@TODO: validation edges! when need the edge informations.
//        LOG.debug("Validating edge by errors output.");
        if (!(source instanceof mxCell)) {
            LOG.debug("source is not mxCell.");
            return result;
        }

        if (!(target instanceof mxCell)) {
//            LOG.debug("target is not mxCell.");
            return result;
        }

        if (!(edge instanceof mxCell)) {
            LOG.debug("edget is not mxCell.");
            return result;
        }

        mxCell cellSource = (mxCell) source;
        mxCell cellTarget = (mxCell) target;
        mxCell cellEdge = (mxCell) edge;

        Element sourceElement = null;
        Element targetElement = null;
        
        if (cellSource.getValue() instanceof Element) {
            sourceElement = (Element) cellSource.getValue();
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }
        
        if (cellTarget.getValue() instanceof Element) {
            targetElement = (Element) cellTarget.getValue();
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }
        
        String sourceTagName = sourceElement.getTagName();
        String targetTagName = targetElement.getTagName();
        
        //System.out.println("CustomGraphBPMN sourceTagName: " + sourceTagName + " targetTagName: " + targetTagName);
        
        String sourceType = cellSource.getAttribute("type");
        String targetType = cellTarget.getAttribute("type");
        
        String edgeTag = ((Element) cellEdge.getValue()).getTagName();
        String edgeType = ((Element) cellEdge.getValue()).getAttribute("type");
        
        if(edgeType.equals("association_flow")){
            System.out.println("source " + sourceTagName);
            System.out.println("target " + targetTagName);
        }
        
        switch (edgeType) {
            case "message_flow":
                if (!sourceTagName.matches("event|activity")
                        || (!targetTagName.matches("event|activity"))) {
                    String error = "Edge '"
                            + "" + edgeType
                            + "' between "
                            + "" + sourceType + " ( " + sourceTagName + " )" + " (source) "
                            + " and "
                            + "" + targetType + " ( " + targetTagName + " )" + " (target) "
                            + " is invalid.";
                    return error;
                }
                break;
            case "association_flow":
                if(sourceTagName.matches("event|activity|gateway|artifact")){
                
                    if(sourceTagName.matches("activity") && targetType.matches("text_annotation|data_object|data_store")){
                        break;
                    } else if (sourceTagName.matches("gateway|event") && targetType.matches("text_annotation")){
                        break;
                    } else if (sourceType.matches("data_object|data_store") && targetTagName.matches("activity")){
                        break;
                    } else{
                        String error = "Edge '"
                            + "" + edgeType
                            + "' between "
                            + "" + sourceType + " ( " + sourceTagName + " )" + " (source) "
                            + " and "
                            + "" + targetType + " ( " + targetTagName + " )" + " (target) "
                            + " is invalid.";
                    return error;
                    }                    
                }                
                break;
            case "sequence_flow":                
                if (!sourceTagName.matches("event|gateway|activity")
                        || (!targetTagName.matches("event|gateway|activity"))) {
                    String error = "Edge '"
                            + "" + edgeType
                            + "' between "
                            + "" + sourceType + " ( " + sourceTagName + " )" + " (source) "
                            + " and "
                            + "" + targetType + " ( " + targetTagName + " )" + " (target) "
                            + " is invalid.";
                    return error;
                }
                break;
        }

        return result;
    }

    @Override
    protected mxGraphView createGraphView() {
        return new CurveGraphView(this);
    }

    /**
     * Override to convert specific IStarMLValue object.
     *
     * @param c
     * @return
     */
    @Override
    public String convertValueToString(Object c) {
        //System.out.println("convert value to string");
        String result = null;
        if (c instanceof mxCell) {
            mxCell cell = (mxCell) c;

            Object v = cell.getValue();
            if (v instanceof Element) {
                Element value = (Element) v;
                result = value.getAttribute("label");

                String type = value.getTagName();

                //System.out.println("type object: " + type);
            } else {
                result = super.convertValueToString(c);
            }
        }
        return result;
    }

    @Override
    public Object getDropTarget(Object[] cells, Point pt, Object cell) {
        //System.out.println("custom graph - get drop target");
        /*
        String fatherType = null;
        String childrenType = null;
        String fatherTagName = null;
        String childrenTagName = null;
        
        // Get father's name
        if (cell instanceof mxCell) {
            mxCell cellElement = (mxCell) cell;
            Object v = cellElement.getValue();

            if (v instanceof Element) {
                Element value = (Element) v;
                fatherType = value.getAttribute("type");
                fatherTagName = value.getTagName();
            }
        }

        // Get children's name
        if (cells[0] != null && cells[0] instanceof mxCell) {
            mxCell cellElement = (mxCell) cells[0];
            Object v = cellElement.getValue();

            if (v instanceof Element) {
                Element value = (Element) v;
                childrenType = value.getAttribute("type");
                childrenTagName = value.getTagName();
            }                        
        }
        */
        return super.getDropTarget(cells, pt, cell);
    }

    @Override
    public void cellLabelChanged(Object cellChanged, Object newValue, boolean autoSize) {
        //System.out.println("cell label changed");
        if (cellChanged instanceof mxCell && newValue != null) {
            mxCell cell = (mxCell) cellChanged;
            Object v = cell.getValue();
            
            if (v instanceof Element) {
                Element value = (Element) v;
                
                String label = newValue.toString();
                value.setAttribute("label", label);
                //se for um grupo, quando ele estiver Collapse e for editado, tbm editar o shape atras...
                if (cell.getChildCount() > 0) {
                    super.cellLabelChanged(cell.getChildAt(0), newValue, autoSize);
                }
                newValue = v;
             
            }
        }
        super.cellLabelChanged(cellChanged, newValue, autoSize);
    }

    @Override
    public boolean isCellEditable(Object cell) {
        System.out.println("is cell editable");
        boolean result = super.isCellEditable(cell);

        /*
        mxCell edge = (mxCell) cell;
        if (edge.isEdge()) {
            result = false;
        }
        */
        return result;
    }

    /**
     * Prints out some useful information about the cell in the tooltip.
     *
     * @param cell
     * @return
     */
    @Override
    public String getToolTipForCell(Object cell) {
        String tip = "<html>";
        mxGeometry geo = getModel().getGeometry(cell);
        mxCellState state = getView().getState(cell);

        if (getModel().isEdge(cell)) {
            tip += "points={";

            if (geo != null) {
                List<mxPoint> points = geo.getPoints();

                if (points != null) {
                    Iterator<mxPoint> it = points.iterator();

                    while (it.hasNext()) {
                        mxPoint point = it.next();
                        tip += "[x=" + numberFormat.format(point.getX())
                                + ",y=" + numberFormat.format(point.getY())
                                + "],";
                    }

                    tip = tip.substring(0, tip.length() - 1);
                }
            }

            tip += "}<br>";
            tip += "absPoints={";

            if (state != null) {

                for (int i = 0; i < state.getAbsolutePointCount(); i++) {
                    mxPoint point = state.getAbsolutePoint(i);
                    tip += "[x=" + numberFormat.format(point.getX())
                            + ",y=" + numberFormat.format(point.getY())
                            + "],";
                }

                tip = tip.substring(0, tip.length() - 1);
            }

            tip += "}";
        } else {
            tip += "geo=[";

            if (geo != null) {
                tip += "x=" + numberFormat.format(geo.getX()) + ",y="
                        + numberFormat.format(geo.getY()) + ",width="
                        + numberFormat.format(geo.getWidth()) + ",height="
                        + numberFormat.format(geo.getHeight());
            }

            tip += "]<br>";
            tip += "state=[";

            if (state != null) {
                tip += "x=" + numberFormat.format(state.getX()) + ",y="
                        + numberFormat.format(state.getY()) + ",width="
                        + numberFormat.format(state.getWidth())
                        + ",height="
                        + numberFormat.format(state.getHeight());
            }

            tip += "]";
        }

        mxPoint trans = getView().getTranslate();

        tip += "<br>scale=" + numberFormat.format(getView().getScale())
                + ", translate=[x=" + numberFormat.format(trans.getX())
                + ",y=" + numberFormat.format(trans.getY()) + "]";
        tip += "</html>";

        return tip;
    }
}
