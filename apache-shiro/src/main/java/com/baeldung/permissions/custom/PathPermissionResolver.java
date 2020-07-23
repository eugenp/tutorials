package com.baeldung.permissions.custom;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

import java.nio.file.Paths;

public class PathPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        return new PathPermission(Paths.get(permissionString));
    }
}
