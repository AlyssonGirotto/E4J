package br.unioeste.jgoose.util;

import java.util.Properties;
/**
 *
 * @author Alysson.Girotto
 */
public class BPMNElement {

    private String tagName; 
    private Properties attributes;

    // Elemento genérico
    public BPMNElement(String tagName, String label, String type) {
            this(tagName, label, type, new Properties());
    }

    public BPMNElement(String tagName, String label, String type, Properties attributes) {
        this.tagName = tagName;
        this.attributes = attributes;
        
        if (this.attributes == null) {
            this.attributes = new Properties();
        }
        
        this.attributes.put("label", label);
        this.attributes.put("type", type);
    }

    // Gateway

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Properties getAttributes() {
        return attributes;
    }

    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "BPMNElement{" + "tagName=" + tagName + ", attributes=" + attributes + '}';
    }        
    
}
