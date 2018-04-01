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
package org.apache.tomcat.jdbc.pool.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorProperty;
import org.apache.tomcat.jdbc.pool.PooledConnection;

/**
 * Slow query report interceptor. Tracks timing of query executions.
 * @author Filip Hanik
 * @version 1.0
 */
public class SlowQueryReport extends AbstractQueryReport  {
    //logger
    private static final Log log = LogFactory.getLog(SlowQueryReport.class);

    /**
     * we will be keeping track of query stats on a per pool basis
     */
    protected static ConcurrentHashMap<String,ConcurrentHashMap<String,QueryStats>> perPoolStats =
        new ConcurrentHashMap<String,ConcurrentHashMap<String,QueryStats>>();
    /**
     * the queries that are used for this interceptor.
     */
    protected volatile ConcurrentHashMap<String,QueryStats> queries = null;
    /**
     * Maximum number of queries we will be storing
     */
    protected int  maxQueries= 1000; //don't store more than this amount of queries

    /**
     * Sort QueryStats by last invocation time
     */
    protected final Comparator<QueryStats> queryStatsComparator = new QueryStatsComparator();

    /**
     * Returns the query stats for a given pool
     * @param poolname - the name of the pool we want to retrieve stats for
     * @return a hash map containing statistics for 0 to maxQueries
     */
    public static ConcurrentHashMap<String,QueryStats> getPoolStats(String poolname) {
        return perPoolStats.get(poolname);
    }

    /**
     * Creates a slow query report interceptor
     */
    public SlowQueryReport() {
        super();
    }

    public void setMaxQueries(int maxQueries) {
        this.maxQueries = maxQueries;
    }


    @Override
    protected String reportFailedQuery(String query, Object[] args, String name, long start, Throwable t) {
        String sql = super.reportFailedQuery(query, args, name, start, t);
        if (this.maxQueries > 0 ) {
            long now = System.currentTimeMillis();
            long delta = now - start;
            QueryStats qs = this.getQueryStats(sql);
            if (qs != null) {
                qs.failure(delta, now);
            }
            if (log.isWarnEnabled()) {
                log.warn("Failed Query Report SQL="+sql+"; time="+delta+" ms;");
            }
        }
        return sql;
    }

    @Override
    protected String reportQuery(String query, Object[] args, final String name, long start, long delta) {
        String sql = super.reportQuery(query, args, name, start, delta);
        if (this.maxQueries > 0 ) {
            QueryStats qs = this.getQueryStats(sql);
            if (qs != null) qs.add(delta, start);
        }
        return sql;
    }

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name, long start, long delta) {
        String sql = super.reportSlowQuery(query, args, name, start, delta);
        if (this.maxQueries > 0 ) {
            QueryStats qs = this.getQueryStats(sql);
            if (qs != null) {
                qs.add(delta, start);
                if (log.isWarnEnabled()) {
                    log.warn("Slow Query Report SQL="+sql+"; time="+delta+" ms;");
                }
            }
        }
        return sql;
    }

    /**
     * invoked when the connection receives the close request
     * Not used for now.
     */
    @Override
    public void closeInvoked() {
        // NOOP
    }

    @Override
    public void prepareStatement(String sql, long time) {
        if (this.maxQueries > 0 ) {
            QueryStats qs = getQueryStats(sql);
            if (qs != null) qs.prepare(time);
        }
    }

    @Override
    public void prepareCall(String sql, long time) {
        if (this.maxQueries > 0 ) {
            QueryStats qs = getQueryStats(sql);
            if (qs != null) qs.prepare(time);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void poolStarted(ConnectionPool pool) {
        super.poolStarted(pool);
        //see if we already created a map for this pool
        queries = SlowQueryReport.perPoolStats.get(pool.getName());
        if (queries==null) {
            //create the map to hold our stats
            //however TODO we need to improve the eviction
            //selection
            queries = new ConcurrentHashMap<String,QueryStats>();
            if (perPoolStats.putIfAbsent(pool.getName(), queries)!=null) {
                //there already was one
                queries = SlowQueryReport.perPoolStats.get(pool.getName());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void poolClosed(ConnectionPool pool) {
        perPoolStats.remove(pool.getName());
        super.poolClosed(pool);
    }

    protected QueryStats getQueryStats(String sql) {
        if (sql==null) sql = "";
        ConcurrentHashMap<String,QueryStats> queries = SlowQueryReport.this.queries;
        if (queries==null) {
            if (log.isWarnEnabled()) log.warn("Connection has already been closed or abandoned");
            return null;
        }
        QueryStats qs = queries.get(sql);
        if (qs == null) {
            qs = new QueryStats(sql);
            if (queries.putIfAbsent(sql,qs)!=null) {
                qs = queries.get(sql);
            } else {
                //we added a new element, see if we need to remove the oldest
                if (queries.size() > maxQueries) {
                    removeOldest(queries);
                }
            }
        }
        return qs;
    }

    /**
     * Sort QueryStats by last invocation time
     * @param queries
     */
    protected void removeOldest(ConcurrentHashMap<String,QueryStats> queries) {
        ArrayList<QueryStats> list = new ArrayList<QueryStats>(queries.values());
        Collections.sort(list, queryStatsComparator);
        int removeIndex = 0;
        while (queries.size() > maxQueries) {
            String sql = list.get(removeIndex).getQuery();
            queries.remove(sql);
            if (log.isDebugEnabled()) log.debug("Removing slow query, capacity reached:"+sql);
            removeIndex++;
        }
    }


    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        super.reset(parent, con);
        if (parent!=null)
            queries = SlowQueryReport.perPoolStats.get(parent.getName());
        else
            queries = null;
    }


    @Override
    public void setProperties(Map<String, InterceptorProperty> properties) {
        super.setProperties(properties);
        final String threshold = "threshold";
        final String maxqueries= "maxQueries";
        InterceptorProperty p1 = properties.get(threshold);
        InterceptorProperty p2 = properties.get(maxqueries);
        if (p1!=null) {
            setThreshold(Long.parseLong(p1.getValue()));
        }
        if (p2!=null) {
            setMaxQueries(Integer.parseInt(p2.getValue()));
        }
    }


    /**
     *
     * @author fhanik
     *
     */
    public static class QueryStats {
        static final String[] FIELD_NAMES = new String[] {
            "query",
            "nrOfInvocations",
            "maxInvocationTime",
            "maxInvocationDate",
            "minInvocationTime",
            "minInvocationDate",
            "totalInvocationTime",
            "failures",
            "prepareCount",
            "prepareTime",
            "lastInvocation"
        };

        static final  String[] FIELD_DESCRIPTIONS = new String[] {
            "The SQL query",
            "The number of query invocations, a call to executeXXX",
            "The longest time for this query in milliseconds",
            "The time and date for when the longest query took place",
            "The shortest time for this query in milliseconds",
            "The time and date for when the shortest query took place",
            "The total amount of milliseconds spent executing this query",
            "The number of failures for this query",
            "The number of times this query was prepared (prepareStatement/prepareCall)",
            "The total number of milliseconds spent preparing this query",
            "The date and time of the last invocation"
        };

        @SuppressWarnings("rawtypes")
        static final OpenType[] FIELD_TYPES = new OpenType[] {
            SimpleType.STRING,
            SimpleType.INTEGER,
            SimpleType.LONG,
            SimpleType.LONG,
            SimpleType.LONG,
            SimpleType.LONG,
            SimpleType.LONG,
            SimpleType.LONG,
            SimpleType.INTEGER,
            SimpleType.LONG,
            SimpleType.LONG
        };

        private final String query;
        private volatile int nrOfInvocations;
        private volatile long maxInvocationTime = Long.MIN_VALUE;
        private volatile long maxInvocationDate;
        private volatile long minInvocationTime = Long.MAX_VALUE;
        private volatile long minInvocationDate;
        private volatile long totalInvocationTime;
        private volatile long failures;
        private volatile int prepareCount;
        private volatile long prepareTime;
        private volatile long lastInvocation = 0;

        public static String[] getFieldNames() {
            return FIELD_NAMES;
        }

        public static String[] getFieldDescriptions() {
            return FIELD_DESCRIPTIONS;
        }

        @SuppressWarnings("rawtypes")
        public static OpenType[] getFieldTypes() {
            return FIELD_TYPES;
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            StringBuilder buf = new StringBuilder("QueryStats[query:");
            buf.append(query);
            buf.append(", nrOfInvocations:");
            buf.append(nrOfInvocations);
            buf.append(", maxInvocationTime:");
            buf.append(maxInvocationTime);
            buf.append(", maxInvocationDate:");
            buf.append(sdf.format(new java.util.Date(maxInvocationDate)));
            buf.append(", minInvocationTime:");
            buf.append(minInvocationTime);
            buf.append(", minInvocationDate:");
            buf.append(sdf.format(new java.util.Date(minInvocationDate)));
            buf.append(", totalInvocationTime:");
            buf.append(totalInvocationTime);
            buf.append(", averageInvocationTime:");
            buf.append((float)totalInvocationTime / (float)nrOfInvocations);
            buf.append(", failures:");
            buf.append(failures);
            buf.append(", prepareCount:");
            buf.append(prepareCount);
            buf.append(", prepareTime:");
            buf.append(prepareTime);
            buf.append("]");
            return buf.toString();
        }

        public CompositeDataSupport getCompositeData(final CompositeType type) throws OpenDataException{
            Object[] values = new Object[] {
                    query,
                    Integer.valueOf(nrOfInvocations),
                    Long.valueOf(maxInvocationTime),
                    Long.valueOf(maxInvocationDate),
                    Long.valueOf(minInvocationTime),
                    Long.valueOf(minInvocationDate),
                    Long.valueOf(totalInvocationTime),
                    Long.valueOf(failures),
                    Integer.valueOf(prepareCount),
                    Long.valueOf(prepareTime),
                    Long.valueOf(lastInvocation)
            };
            return new CompositeDataSupport(type,FIELD_NAMES,values);
        }

        public QueryStats(String query) {
            this.query = query;
        }

        public void prepare(long invocationTime) {
            prepareCount++;
            prepareTime+=invocationTime;

        }

        public void add(long invocationTime, long now) {
            //not thread safe, but don't sacrifice performance for this kind of stuff
            maxInvocationTime = Math.max(invocationTime, maxInvocationTime);
            if (maxInvocationTime == invocationTime) {
                maxInvocationDate = now;
            }
            minInvocationTime = Math.min(invocationTime, minInvocationTime);
            if (minInvocationTime==invocationTime) {
                minInvocationDate = now;
            }
            nrOfInvocations++;
            totalInvocationTime+=invocationTime;
            lastInvocation = now;
        }

        public void failure(long invocationTime, long now) {
            add(invocationTime,now);
            failures++;

        }

        public String getQuery() {
            return query;
        }

        public int getNrOfInvocations() {
            return nrOfInvocations;
        }

        public long getMaxInvocationTime() {
            return maxInvocationTime;
        }

        public long getMaxInvocationDate() {
            return maxInvocationDate;
        }

        public long getMinInvocationTime() {
            return minInvocationTime;
        }

        public long getMinInvocationDate() {
            return minInvocationDate;
        }

        public long getTotalInvocationTime() {
            return totalInvocationTime;
        }

        @Override
        public int hashCode() {
            return query.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof QueryStats) {
                QueryStats qs = (QueryStats)other;
                return qs.query.equals(this.query);
            }
            return false;
        }

        public boolean isOlderThan(QueryStats other) {
            return this.lastInvocation < other.lastInvocation;
        }
    }

    /** Compare QueryStats by their lastInvocation value. QueryStats that
     * have never been updated, have a lastInvocation value of {@code 0}
     * which should be handled as the newest possible invocation.
     */
    private static class QueryStatsComparator implements Comparator<QueryStats> {

        @Override
        public int compare(QueryStats stats1, QueryStats stats2) {
            return compare(handleZero(stats1.lastInvocation),
                    handleZero(stats2.lastInvocation));
        }

        private static long handleZero(long value) {
            return value == 0 ? Long.MAX_VALUE : value;
        }

        private static int compare(long a, long b) {
            if (a < b) {
                return -1;
            } else if (a > b) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
