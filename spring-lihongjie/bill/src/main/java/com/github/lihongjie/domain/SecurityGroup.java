package com.github.lihongjie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.Set;

/**
 * Created by lihongjie on 7/21/17.
 *
 *
 */
@Entity(name = "SECURITY_GROUP")
public class SecurityGroup {

    @Id
    @Column(name = "GROUP_ID", length = 20)
    private String groupId;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "LAST_UPDATED_STAMP")
    private Date lastUpdatedStamp;
    @Column(name = "LAST_UPDATED_TX_STAMP")
    private Date lastUpdatedTxStamp;
    @Column(name = "CREATED_STAMP")
    private Date createdStamp;
    @Column(name = "CREATED_TX_STAMP")
    private Date createdTxStamp;

    @ManyToMany
    private Set<User> users;

    @ManyToMany
    private Set<SecurityPermission> securityPermissions;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Date getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }
}
