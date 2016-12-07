package org.words.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.words.gui.WordMasterView;

/**
 * Created by Eric on 2016/12/2.
 */
public class GuiModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(WordMasterView.class).in(Singleton.class);
    }
}
