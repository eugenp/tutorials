package org.baeldung.web.util;

import java.util.Date;

public class ActiveUserView {

    public ActiveUserView() {
    }
    
    private Date activeTime;
    private String ipAddress;
    private String email;
    public Date getActiveTime() {
        return activeTime;
    }
    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activeTime == null) ? 0 : activeTime.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActiveUserView other = (ActiveUserView) obj;
        if (activeTime == null) {
            if (other.activeTime != null)
                return false;
        } else if (!activeTime.equals(other.activeTime))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (ipAddress == null) {
            if (other.ipAddress != null)
                return false;
        } else if (!ipAddress.equals(other.ipAddress))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ActiveUserView [activeTime=" + activeTime + ", ipAddress=" + ipAddress + ", email=" + email + "]";
    }

}
