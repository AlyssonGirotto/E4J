package br.unioeste.jgoose.util;

import com.mxgraph.util.mxDomUtils;
import java.util.Properties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Alysson.Girotto
 */
public class BPMNUtils {
    
    public static Element create(BPMNElement bpmnElement) {
        Document doc = mxDomUtils.createDocument();
        Element element = doc.createElement(bpmnElement.getTagName());

        Properties prop = bpmnElement.getAttributes();
        for (Object k : prop.keySet()) {
            String key = (String) k;
            element.setAttribute(key, prop.getProperty(key));
        }
        return element;
    }
    
    public static Element createPool () { return create(new BPMNElement("swimlane", "Pool", "pool")); }
    public static Element createLane () { return create(new BPMNElement("swimlane", "Lane", "lane")); }
    
    public static Element createGroup () { return create(new BPMNElement("artifact", "Group", "group")); }
    public static Element createTextAnnotation () { return create(new BPMNElement("artifact", "Text annotation", "text_annotation")); }
    public static Element createDataStore () { return create(new BPMNElement("artifact", "Data Store", "data_store")); }
    public static Element createDataObject () { return create(new BPMNElement("artifact", "Data Object", "data_object")); }
    
    public static Element createGateway () { return create(new BPMNElement("gateway", "Gateway", "gateway")); }
    public static Element createGatewayParallel () { return create(new BPMNElement("gateway", "Gateway Parallel", "parallel")); }
    public static Element createGatewayInclusive () { return create(new BPMNElement("gateway", "Gateway Inclusive", "inclusive")); }
    public static Element createGatewayEventBased () { return create(new BPMNElement("gateway", "Gateway Event-Based", "event_based")); }
    public static Element createGatewayExclusiveEventBased () { return create(new BPMNElement("gateway", "Gateway Exclusive Event-Based", "exclusive_event_based")); }
    public static Element createGatewayExclusive () { return create(new BPMNElement("gateway", "Gatway Exclusive", "exclusive")); }
    public static Element createGatewayParallelEventBased () { return create(new BPMNElement("gateway", "Gateway Parallel Event-Based", "parallel_event_based")); }
    public static Element createGatewayComplex () { return create(new BPMNElement("gateway", "Gateway Complex", "complex")); }
    
    public static Element createSubprocess () { return create(new BPMNElement("activity", "Subprocess", "subprocess")); }
    public static Element createTask () { return create(new BPMNElement("activity", "Task", "task")); }
      
    public static Element createStartEvent () { return create(new BPMNElement("event", "Start Event", "start_event")); }
    public static Element createEndEvent () { return create(new BPMNElement("event", "End Event", "end_event")); }
    public static Element createIntermediateEvent () { return create(new BPMNElement("event", "Intermediate Event", "intermediate_event")); }
    
    public static Element createSequenceFlow () { return create(new BPMNElement("flow", "Sequence Flow", "sequence_flow")); }
    public static Element createMessageFlow () { return create(new BPMNElement("flow", "Message Flow", "message_flow")); }
    public static Element createAssociationFlow () { return create(new BPMNElement("flow", "Association Flow", "association_flow")); }  
}
