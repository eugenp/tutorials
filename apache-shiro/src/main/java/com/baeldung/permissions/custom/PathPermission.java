package com.baeldung.permissions.custom;

import org.apache.shiro.authz.Permission;

import java.nio.file.Path;

public class PathPermission implements Permission {

    private final Path path;

    public PathPermission(Path path) {
        this.path = path;
    }

    @Override
    public boolean implies(Permission p) {
        if(p instanceof PathPermission) {
            return ((PathPermission) p).path.startsWith(path);
        }
        return false;
    }
}
