package org.words.to;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base TO class
 */
public class AbstractTO {

    protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /**
     * Support for JForm bean binding
     * @param listener listener
     */
    @SuppressWarnings("unused")
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Support for JForm bean binding
     * @param listener listener
     */
    @SuppressWarnings("unused")
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
