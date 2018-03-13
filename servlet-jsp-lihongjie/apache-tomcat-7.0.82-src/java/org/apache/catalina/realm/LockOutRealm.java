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

package org.apache.catalina.realm;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.LifecycleException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSName;

/**
 * This class extends the CombinedRealm (hence it can wrap other Realms) to
 * provide a user lock out mechanism if there are too many failed
 * authentication attempts in a given period of time. To ensure correct
 * operation, there is a reasonable degree of synchronisation in this Realm.
 * This Realm does not require modification to the underlying Realms or the
 * associated user storage mechanisms. It achieves this by recording all failed
 * logins, including those for users that do not exist. To prevent a DOS by
 * deliberating making requests with invalid users (and hence causing this cache
 * to grow) the size of the list of users that have failed authentication is
 * limited.
 */
public class LockOutRealm extends CombinedRealm {

    private static final Log log = LogFactory.getLog(LockOutRealm.class);

    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String name = "LockOutRealm";

    /**
     * The number of times in a row a user has to fail authentication to be
     * locked out. Defaults to 5.
     */
    protected int failureCount = 5;
    
    /**
     * The time (in seconds) a user is locked out for after too many
     * authentication failures. Defaults to 300 (5 minutes). 
     */
    protected int lockOutTime = 300;

    /**
     * Number of users that have failed authentication to keep in cache. Over
     * time the cache will grow to this size and may not shrink. Defaults to
     * 1000.
     */
    protected int cacheSize = 1000;

    /**
     * If a failed user is removed from the cache because the cache is too big
     * before it has been in the cache for at least this period of time (in
     * seconds) a warning message will be logged. Defaults to 3600 (1 hour).
     */
    protected int cacheRemovalWarningTime = 3600;

    /**
     * Users whose last authentication attempt failed. Entries will be ordered
     * in access order from least recent to most recent.
     */
    protected Map<String,LockRecord> failedUsers = null;


    /**
     * Prepare for the beginning of active use of the public methods of this
     * component and implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {
        // Configure the list of failed users to delete the oldest entry once it
        // exceeds the specified size
        failedUsers = new LinkedHashMap<String, LockRecord>(cacheSize, 0.75f,
                true) {
            private static final long serialVersionUID = 1L;
            @Override
            protected boolean removeEldestEntry(
                    Map.Entry<String, LockRecord> eldest) {
                if (size() > cacheSize) {
                    // Check to see if this element has been removed too quickly
                    long timeInCache = (System.currentTimeMillis() -
                            eldest.getValue().getLastFailureTime())/1000;
                    
                    if (timeInCache < cacheRemovalWarningTime) {
                        log.warn(sm.getString("lockOutRealm.removeWarning",
                                eldest.getKey(), Long.valueOf(timeInCache)));
                    }
                    return true;
                }
                return false;
            }
        };

        super.startInternal();
    }


    /**
     * Return the Principal associated with the specified username, which
     * matches the digest calculated using the given parameters using the
     * method described in RFC 2069; otherwise return <code>null</code>.
     *
     * @param username Username of the Principal to look up
     * @param clientDigest Digest which has been submitted by the client
     * @param nonce Unique (or supposedly unique) token which has been used
     * for this request
     * @param realmName Realm name
     * @param md5a2 Second MD5 digest used to calculate the digest :
     * MD5(Method + ":" + uri)
     */
    @Override
    public Principal authenticate(String username, String clientDigest,
            String nonce, String nc, String cnonce, String qop,
            String realmName, String md5a2) {

        Principal authenticatedUser = super.authenticate(username, clientDigest, nonce, nc, cnonce,
                qop, realmName, md5a2);
        return filterLockedAccounts(username, authenticatedUser);
    }


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public Principal authenticate(String username, String credentials) {
        Principal authenticatedUser = super.authenticate(username, credentials);
        return filterLockedAccounts(username, authenticatedUser);
    }


    /**
     * Return the Principal associated with the specified chain of X509
     * client certificates.  If there is none, return <code>null</code>.
     *
     * @param certs Array of client certificates, with the first one in
     *  the array being the certificate of the client itself.
     */
    @Override
    public Principal authenticate(X509Certificate[] certs) {
        String username = null;
        if (certs != null && certs.length >0) {
            username = certs[0].getSubjectDN().getName();
        }

        Principal authenticatedUser = super.authenticate(certs);
        return filterLockedAccounts(username, authenticatedUser);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Principal authenticate(GSSContext gssContext, boolean storeCreds) {
        if (gssContext.isEstablished()) {
            String username = null;
            GSSName name = null;
            try {
                name = gssContext.getSrcName();
            } catch (GSSException e) {
                log.warn(sm.getString("realmBase.gssNameFail"), e);
                return null;
            }
            
            username = name.toString();
            
            Principal authenticatedUser = super.authenticate(gssContext, storeCreds);
                
            return filterLockedAccounts(username, authenticatedUser);
        }
        
        // Fail in all other cases
        return null;
    }


    /*
     * Filters authenticated principals to ensure that <code>null</code> is
     * returned for any user that is currently locked out.
     */
    private Principal filterLockedAccounts(String username, Principal authenticatedUser) {
        // Register all failed authentications
        if (authenticatedUser == null) {
            registerAuthFailure(username);
        }

        if (isLocked(username)) {
            // If the user is currently locked, authentication will always fail
            log.warn(sm.getString("lockOutRealm.authLockedUser", username));
            return null;
        }

        if (authenticatedUser != null) {
            registerAuthSuccess(username);
        }

        return authenticatedUser;
    }


    /**
     * Unlock the specified username. This will remove all records of
     * authentication failures for this user.
     * 
     * @param username The user to unlock
     */
    public void unlock(String username) {
        // Auth success clears the lock record so... 
        registerAuthSuccess(username);
    }
    
    /*
     * Checks to see if the current user is locked. If this is associated with
     * a login attempt, then the last access time will be recorded and any
     * attempt to authenticated a locked user will log a warning.
     */
    private boolean isLocked(String username) {
        LockRecord lockRecord = null;
        synchronized (this) {
            lockRecord = failedUsers.get(username);
        }
        
        // No lock record means user can't be locked
        if (lockRecord == null) {
            return false;
        }
        
        // Check to see if user is locked
        if (lockRecord.getFailures() >= failureCount &&
                (System.currentTimeMillis() -
                        lockRecord.getLastFailureTime())/1000 < lockOutTime) {
            return true;
        }
        
        // User has not, yet, exceeded lock thresholds
        return false;
    }


    /*
     * After successful authentication, any record of previous authentication
     * failure is removed.
     */
    private synchronized void registerAuthSuccess(String username) {
        // Successful authentication means removal from the list of failed users
        failedUsers.remove(username);
    }


    /*
     * After a failed authentication, add the record of the failed
     * authentication. 
     */
    private void registerAuthFailure(String username) {
        LockRecord lockRecord = null;
        synchronized (this) {
            if (!failedUsers.containsKey(username)) {
                lockRecord = new LockRecord(); 
                failedUsers.put(username, lockRecord);
            } else {
                lockRecord = failedUsers.get(username);
                if (lockRecord.getFailures() >= failureCount &&
                        ((System.currentTimeMillis() -
                                lockRecord.getLastFailureTime())/1000)
                                > lockOutTime) {
                    // User was previously locked out but lockout has now
                    // expired so reset failure count
                    lockRecord.setFailures(0);
                }
            }
        }
        lockRecord.registerFailure();
    }

    
    /**
     * Get the number of failed authentication attempts required to lock the
     * user account.
     * @return the failureCount
     */
    public int getFailureCount() {
        return failureCount;
    }


    /**
     * Set the number of failed authentication attempts required to lock the
     * user account.
     * @param failureCount the failureCount to set
     */
    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }


    /**
     * Get the period for which an account will be locked.
     * @return the lockOutTime
     */
    public int getLockOutTime() {
        return lockOutTime;
    }


    @Override
    protected String getName() {
        return name;
    }


    /**
     * Set the period for which an account will be locked.
     * @param lockOutTime the lockOutTime to set
     */
    public void setLockOutTime(int lockOutTime) {
        this.lockOutTime = lockOutTime;
    }


    /**
     * Get the maximum number of users for which authentication failure will be
     * kept in the cache.
     * @return the cacheSize
     */
    public int getCacheSize() {
        return cacheSize;
    }


    /**
     * Set the maximum number of users for which authentication failure will be
     * kept in the cache.
     * @param cacheSize the cacheSize to set
     */
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }


    /**
     * Get the minimum period a failed authentication must remain in the cache
     * to avoid generating a warning if it is removed from the cache to make
     * space for a new entry.
     * @return the cacheRemovalWarningTime
     */
    public int getCacheRemovalWarningTime() {
        return cacheRemovalWarningTime;
    }


    /**
     * Set the minimum period a failed authentication must remain in the cache
     * to avoid generating a warning if it is removed from the cache to make
     * space for a new entry.
     * @param cacheRemovalWarningTime the cacheRemovalWarningTime to set
     */
    public void setCacheRemovalWarningTime(int cacheRemovalWarningTime) {
        this.cacheRemovalWarningTime = cacheRemovalWarningTime;
    }


    protected static class LockRecord {
        private AtomicInteger failures = new AtomicInteger(0);
        private long lastFailureTime = 0;
        
        public int getFailures() {
            return failures.get();
        }
        
        public void setFailures(int theFailures) {
            failures.set(theFailures);
        }

        public long getLastFailureTime() {
            return lastFailureTime;
        }
        
        public void registerFailure() {
            failures.incrementAndGet();
            lastFailureTime = System.currentTimeMillis();
        }
    }
}
