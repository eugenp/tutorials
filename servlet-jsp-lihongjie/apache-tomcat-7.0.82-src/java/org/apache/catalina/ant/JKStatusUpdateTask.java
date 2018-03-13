/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.ant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.tools.ant.BuildException;

/**
 * Ant task that implements the <code>/status</code> command, supported by the
 * mod_jk status (1.2.9) application.
 * 
 * @author Peter Rossbach
 * @since 5.5.9
 */
public class JKStatusUpdateTask extends AbstractCatalinaTask {

    private String worker = "lb";

    private String workerType = "lb";

    private int internalid = 0;

    private Integer lbRetries;

    private Integer lbRecovertime;

    private Boolean lbStickySession = Boolean.TRUE;

    private Boolean lbForceSession = Boolean.FALSE;

    private Integer workerLoadFactor;

    private String workerRedirect;

    private String workerClusterDomain;

    private Boolean workerDisabled = Boolean.FALSE;

    private Boolean workerStopped = Boolean.FALSE;
    
    private boolean isLBMode = true;

    private String workerLb;

    /**
     *  
     */
    public JKStatusUpdateTask() {
        super();
        setUrl("http://localhost/status");
    }

    /**
     * @return Returns the internalid.
     */
    public int getInternalid() {
        return internalid;
    }

    /**
     * @param internalid
     *            The internalid to set.
     */
    public void setInternalid(int internalid) {
        this.internalid = internalid;
    }

    /**
     * @return Returns the lbForceSession.
     */
    public Boolean getLbForceSession() {
        return lbForceSession;
    }

    /**
     * @param lbForceSession
     *            The lbForceSession to set.
     */
    public void setLbForceSession(Boolean lbForceSession) {
        this.lbForceSession = lbForceSession;
    }

    /**
     * @return Returns the lbRecovertime.
     */
    public Integer getLbRecovertime() {
        return lbRecovertime;
    }

    /**
     * @param lbRecovertime
     *            The lbRecovertime to set.
     */
    public void setLbRecovertime(Integer lbRecovertime) {
        this.lbRecovertime = lbRecovertime;
    }

    /**
     * @return Returns the lbRetries.
     */
    public Integer getLbRetries() {
        return lbRetries;
    }

    /**
     * @param lbRetries
     *            The lbRetries to set.
     */
    public void setLbRetries(Integer lbRetries) {
        this.lbRetries = lbRetries;
    }

    /**
     * @return Returns the lbStickySession.
     */
    public Boolean getLbStickySession() {
        return lbStickySession;
    }

    /**
     * @param lbStickySession
     *            The lbStickySession to set.
     */
    public void setLbStickySession(Boolean lbStickySession) {
        this.lbStickySession = lbStickySession;
    }

    /**
     * @return Returns the worker.
     */
    public String getWorker() {
        return worker;
    }

    /**
     * @param worker
     *            The worker to set.
     */
    public void setWorker(String worker) {
        this.worker = worker;
    }

    /**
     * @return Returns the workerType.
     */
    public String getWorkerType() {
        return workerType;
    }

    /**
     * @param workerType
     *            The workerType to set.
     */
    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    /**
     * @return Returns the workerLb.
     */
    public String getWorkerLb() {
        return workerLb;
    }

    /**
     * @param workerLb
     *            The workerLb to set.
     */
    public void setWorkerLb(String workerLb) {
        this.workerLb = workerLb;
    }

    /**
     * @return Returns the workerClusterDomain.
     */
    public String getWorkerClusterDomain() {
        return workerClusterDomain;
    }

    /**
     * @param workerClusterDomain
     *            The workerClusterDomain to set.
     */
    public void setWorkerClusterDomain(String workerClusterDomain) {
        this.workerClusterDomain = workerClusterDomain;
    }

    /**
     * @return Returns the workerDisabled.
     */
    public Boolean getWorkerDisabled() {
        return workerDisabled;
    }

    /**
     * @param workerDisabled
     *            The workerDisabled to set.
     */
    public void setWorkerDisabled(Boolean workerDisabled) {
        this.workerDisabled = workerDisabled;
    }

    /**
     * @return Returns the workerStopped.
     */
    public Boolean getWorkerStopped() {
        return workerStopped;
    }
    
    /**
     * @param workerStopped The workerStopped to set.
     */
    public void setWorkerStopped(Boolean workerStopped) {
        this.workerStopped = workerStopped;
    }
    
    /**
     * @return Returns the workerLoadFactor.
     */
    public Integer getWorkerLoadFactor() {
        return workerLoadFactor;
    }

    /**
     * @param workerLoadFactor
     *            The workerLoadFactor to set.
     */
    public void setWorkerLoadFactor(Integer workerLoadFactor) {
        this.workerLoadFactor = workerLoadFactor;
    }

    /**
     * @return Returns the workerRedirect.
     */
    public String getWorkerRedirect() {
        return workerRedirect;
    }

    /**
     * @param workerRedirect
     *            The workerRedirect to set.
     */
    public void setWorkerRedirect(String workerRedirect) {
        this.workerRedirect = workerRedirect;
    }

    /**
     * Execute the requested operation.
     * 
     * @exception BuildException
     *                if an error occurs
     */
    @Override
    public void execute() throws BuildException {

        super.execute();
        checkParameter();
        StringBuilder sb = createLink();
        execute(sb.toString(), null, null, -1);

    }

    /**
     * Create JkStatus link
     * <ul>
     * <li><b>load balance example:
     * </b>http://localhost/status?cmd=update&mime=txt&w=lb&lf=false&ls=true</li>
     * <li><b>worker example:
     * </b>http://localhost/status?cmd=update&mime=txt&w=node1&l=lb&wf=1&wd=false&ws=false
     * </li>
     * </ul>
     * 
     * @return create jkstatus link
     */
    private StringBuilder createLink() {
        // Building URL
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("?cmd=update&mime=txt");
            sb.append("&w=");
            sb.append(URLEncoder.encode(worker, getCharset()));

            if (isLBMode) {
                //http://localhost/status?cmd=update&mime=txt&w=lb&lf=false&ls=true
                if ((lbRetries != null)) { // > 0
                    sb.append("&lr=");
                    sb.append(lbRetries);
                }
                if ((lbRecovertime != null)) { // > 59
                    sb.append("&lt=");
                    sb.append(lbRecovertime);
                }
                if ((lbStickySession != null)) {
                    sb.append("&ls=");
                    sb.append(lbStickySession);
                }
                if ((lbForceSession != null)) {
                    sb.append("&lf=");
                    sb.append(lbForceSession);
                }
            } else {
                //http://localhost/status?cmd=update&mime=txt&w=node1&l=lb&wf=1&wd=false&ws=false
                if ((workerLb != null)) { // must be configured
                    sb.append("&l=");
                    sb.append(URLEncoder.encode(workerLb, getCharset()));
                }
                if ((workerLoadFactor != null)) { // >= 1
                    sb.append("&wf=");
                    sb.append(workerLoadFactor);
                }
                if ((workerDisabled != null)) {
                    sb.append("&wd=");
                    sb.append(workerDisabled);
                }
                if ((workerStopped != null)) {
                    sb.append("&ws=");
                    sb.append(workerStopped);
                }
                if ((workerRedirect != null)) { // other worker conrecte lb's
                    sb.append("&wr=");
                }
                if ((workerClusterDomain != null)) {
                    sb.append("&wc=");
                    sb.append(URLEncoder.encode(workerClusterDomain,
                            getCharset()));
                }
            }

        } catch (UnsupportedEncodingException e) {
            throw new BuildException("Invalid 'charset' attribute: "
                    + getCharset());
        }
        return sb;
    }

    /**
     * check correct lb and worker parameter
     */
    protected void checkParameter() {
        if (worker == null) {
            throw new BuildException("Must specify 'worker' attribute");
        }
        if (workerType == null) {
            throw new BuildException("Must specify 'workerType' attribute");
        }
        if ("lb".equals(workerType)) {
            if (lbRecovertime == null && lbRetries == null) {
                throw new BuildException(
                        "Must specify at a lb worker either 'lbRecovertime' or"
                                + "'lbRetries' attribute");
            }
            if (lbStickySession == null || lbForceSession == null) {
                throw new BuildException("Must specify at a lb worker either"
                        + "'lbStickySession' and 'lbForceSession' attribute");
            }
            if (null != lbRecovertime && 60 < lbRecovertime.intValue()) {
                throw new BuildException(
                        "The 'lbRecovertime' must be greater than 59");
            }
            if (null != lbRetries && 1 < lbRetries.intValue()) {
                throw new BuildException(
                        "The 'lbRetries' must be greater than 1");
            }
            isLBMode = true;
        } else if ("worker".equals(workerType)) {
            if (workerDisabled == null) {
                throw new BuildException(
                        "Must specify at a node worker 'workerDisabled' attribute");
            }
            if (workerStopped == null) {
                throw new BuildException(
                        "Must specify at a node worker 'workerStopped' attribute");
            }
            if (workerLoadFactor == null ) {
                throw new BuildException(
                        "Must specify at a node worker 'workerLoadFactor' attribute");
            }
            if (workerClusterDomain == null) {
                throw new BuildException(
                        "Must specify at a node worker 'workerClusterDomain' attribute");
            }
            if (workerRedirect == null) {
                throw new BuildException(
                        "Must specify at a node worker 'workerRedirect' attribute");
            }
            if (workerLb == null) {
                throw new BuildException("Must specify 'workerLb' attribute");
            }
            if (workerLoadFactor.intValue() < 1) {
                throw new BuildException(
                        "The 'workerLoadFactor' must be greater or equal 1");
            }
            isLBMode = false;
        } else {
            throw new BuildException(
                    "Only 'lb' and 'worker' supported as workerType attribute");
        }
    }
}