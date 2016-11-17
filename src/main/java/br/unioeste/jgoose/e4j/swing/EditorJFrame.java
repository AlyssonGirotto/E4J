package br.unioeste.jgoose.e4j.swing;

import br.unioeste.jgoose.e4j.swing.menubar.EditorMenuBar;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Leonardo Merlin - leonardo.merlin at unioeste.br
 */
public class EditorJFrame extends JFrame {

    private BasicGraphEditor editor;
    private EditorMenuBar menubar;

    public EditorJFrame(int type) throws HeadlessException, IOException {
        
        mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
        mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";

        if (type == 0) { // add editor istar
            this.editor = new BasicIStarEditor(this);
            this.getContentPane().add(this.editor);
        } else if (type == 1){ // add editor use cases
            this.editor = new BasicUseCasesEditor(this);
            this.getContentPane().add(this.editor);
        } else if (type == 2){ // add bpmn editor
            this.editor = new BasicBPMNEditor(this);
            this.getContentPane().add(this.editor);   
        }
        // add menubar
        this.menubar = new EditorMenuBar(editor);
        this.setJMenuBar(menubar);
        this.setSize(870, 640);
    }

    void exit() {
        int defaultCloseOperation = getDefaultCloseOperation();
        switch (defaultCloseOperation) {
            case JFrame.DISPOSE_ON_CLOSE:
                dispose();
                break;
            case JFrame.DO_NOTHING_ON_CLOSE:
                break;
            case JFrame.EXIT_ON_CLOSE:
                dispose();
                System.exit(0);
                break;
            case JFrame.HIDE_ON_CLOSE:
                this.setVisible(false);
                WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
                break;
        }
    }

    public BasicGraphEditor getEditor() {
        return editor;
    }

    public void setEditor(BasicIStarEditor editor) {
        this.editor = editor;
    }
}
