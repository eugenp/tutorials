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
package org.apache.catalina.tribes.group.interceptors;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.AbsoluteOrder;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;

/**
 * A dinky coordinator, just uses a sorted version of the member array.
 * 
 * @author rnewson
 * 
 */
public class SimpleCoordinator extends ChannelInterceptorBase {

    private Member[] view;

    private AtomicBoolean membershipChanged = new AtomicBoolean();

    private void membershipChanged() {
        membershipChanged.set(true);
    }

    @Override
    public void memberAdded(final Member member) {
        super.memberAdded(member);
        membershipChanged();
        installViewWhenStable();
    }

    @Override
    public void memberDisappeared(final Member member) {
        super.memberDisappeared(member);
        membershipChanged();
        installViewWhenStable();
    }

    /**
     * Override to receive view changes.
     * 
     * @param view
     */
    protected void viewChange(final Member[] view) {
    }

    @Override
    public void start(int svc) throws ChannelException {
        super.start(svc);
        installViewWhenStable();
    }

    private void installViewWhenStable() {
        int stableCount = 0;

        while (stableCount < 10) {
            if (membershipChanged.compareAndSet(true, false)) {
                stableCount = 0;
            } else {
                stableCount++;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        final Member[] members = getMembers();
        final Member[] view = new Member[members.length+1];
        System.arraycopy(members, 0, view, 0, members.length);
        view[members.length] = getLocalMember(false);
        Arrays.sort(view, AbsoluteOrder.comp);
        if (Arrays.equals(view, this.view)) {
            return;
        }
        this.view = view;
        viewChange(view);
    }

    @Override
    public void stop(int svc) throws ChannelException {
        super.stop(svc);
    }

    public Member[] getView() {
        return view;
    }

    public Member getCoordinator() {
        return view == null ? null : view[0];
    }

    public boolean isCoordinator() {
        return view == null ? false : getLocalMember(false).equals(
                getCoordinator());
    }

}
