package com.baeldung.bootique.module;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class ModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new ModuleBinder();
    }

}
