package com.danifoldi.cpscheckaac.inject;

import com.danifoldi.cpscheckaac.CPSCheckAAC;
import com.danifoldi.cpscheckaac.PluginLoader;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;

@Singleton
@Component(modules = ProvidingModule.class)
public interface PluginComponent {

    PluginLoader loader();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder bindPlugin(final CPSCheckAAC plugin);

        @BindsInstance
        Builder bindDatafolder(final @Named("datafolder") Path datafolder);

        PluginComponent build();
    }
}