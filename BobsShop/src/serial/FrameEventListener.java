/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serial;
/**
 * The listener interface for receiving FrameEvent's.
 * The class that is interested in processing a FrameEvent implements this
 * interface, and the object created with that class is registered with a
 * SerialFrame instance using the addFrameEventListener method.
 * When the action event occurs, that object's frameReady method is invoked.
 *
 * @author Lars Mortensen, Ian Bridgwood
 * @version 16/02/2010
 */
public interface FrameEventListener extends java.util.EventListener {

    /**
     * Invoked when a <code>FrameEvent</code> occurs.
     * @param frameEvent the <code>FrameEvent</code>
     */
    public void frameReady(FrameEvent frameEvent);
}
