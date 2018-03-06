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

package org.apache.naming.resources;

import java.util.HashMap;
import java.util.Random;


/**
 * Implements a special purpose cache.
 * 
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 */
public class ResourceCache {
    
    
    // ----------------------------------------------------------- Constructors
    
    
    public ResourceCache() {
        // NO-OP
    }
    
    
    // ----------------------------------------------------- Instance Variables


    /**
     * Random generator used to determine elements to free.
     */
    protected Random random = new Random();


    /**
     * Cache.
     * Path -> Cache entry.
     */
    protected CacheEntry[] cache = new CacheEntry[0];


    /**
     * Not found cache.
     */
    protected HashMap<String,CacheEntry> notFoundCache =
        new HashMap<String,CacheEntry>();


    /**
     * Max size of resources which will have their content cached.
     */
    protected int cacheMaxSize = 10240; // 10 MB


    /**
     * Max amount of removals during a make space.
     */
    protected int maxAllocateIterations = 20;


    /**
     * Entry hit ratio at which an entry will never be removed from the cache.
     * Compared with entry.access / hitsCount
     */
    protected long desiredEntryAccessRatio = 3;


    /**
     * Spare amount of not found entries.
     */
    protected int spareNotFoundEntries = 500;


    /**
     * Current cache size in KB.
     */
    protected int cacheSize = 0;


    /**
     * Number of accesses to the cache.
     */
    protected long accessCount = 0;


    /**
     * Number of cache hits.
     */
    protected long hitsCount = 0;


    // ------------------------------------------------------------- Properties


    /**
     * Return the access count.
     * Note: Update is not synced, so the number may not be completely 
     * accurate.
     * TODO: Currently unused. Expose via JMX?
     */
    public long getAccessCount() {
        return accessCount;
    }


    /**
     * Return the maximum size of the cache in KB.
     * TODO: Currently unused. Expose via JMX?
     */
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }


    /**
     * Set the maximum size of the cache in KB.
     */
    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }


    /**
     * Return the current cache size in KB.
     * TODO: Currently unused. Expose via JMX?
     */
    public int getCacheSize() {
        return cacheSize;
    }


    /**
     * Return desired entry access ratio.
     * @deprecated - unused
     */
    @Deprecated
    public long getDesiredEntryAccessRatio() {
        return desiredEntryAccessRatio;
    }


    /**
     * Set the desired entry access ratio.
     * @deprecated - unused
     */
    @Deprecated
    public void setDesiredEntryAccessRatio(long desiredEntryAccessRatio) {
        this.desiredEntryAccessRatio = desiredEntryAccessRatio;
    }


    /**
     * Return the number of cache hits.
     * Note: Update is not synced, so the number may not be completely 
     * accurate.
     * TODO: Currently unused. Expose via JMX?
     */
    public long getHitsCount() {
        return hitsCount;
    }


    /**
     * Return the maximum amount of iterations during a space allocation.
     * @deprecated - unused
     */
    @Deprecated
    public int getMaxAllocateIterations() {
        return maxAllocateIterations;
    }


    /**
     * Set the maximum amount of iterations during a space allocation.
     * @deprecated - unused
     */
    @Deprecated
    public void setMaxAllocateIterations(int maxAllocateIterations) {
        this.maxAllocateIterations = maxAllocateIterations;
    }


    /**
     * Return the amount of spare not found entries.
     * @deprecated - unused
     */
    @Deprecated
    public int getSpareNotFoundEntries() {
        return spareNotFoundEntries;
    }


    /**
     * Set the amount of spare not found entries.
     * @deprecated - unused
     */
    @Deprecated
    public void setSpareNotFoundEntries(int spareNotFoundEntries) {
        this.spareNotFoundEntries = spareNotFoundEntries;
    }


    // --------------------------------------------------------- Public Methods


    public boolean allocate(int space) {

        int toFree = space - (cacheMaxSize - cacheSize);

        if (toFree <= 0) {
            return true;
        }

        // Increase the amount to free so that allocate won't have to run right
        // away again
        toFree += (cacheMaxSize / 20);

        int size = notFoundCache.size();
        if (size > spareNotFoundEntries) {
            notFoundCache.clear();
            cacheSize -= size;
            toFree -= size;
        }

        if (toFree <= 0) {
            return true;
        }

        int attempts = 0;
        int entriesFound = 0;
        long totalSpace = 0;
        int[] toRemove = new int[maxAllocateIterations];
        while (toFree > 0) {
            if (attempts == maxAllocateIterations) {
                // Give up, no changes are made to the current cache
                return false;
            }
            // Randomly select an entry in the array
            int entryPos = -1;
            boolean unique = false;
            while (!unique) {
                unique = true;
                entryPos = random.nextInt(cache.length) ;
                // Guarantee uniqueness
                for (int i = 0; i < entriesFound; i++) {
                    if (toRemove[i] == entryPos) {
                        unique = false;
                    }
                }
            }
            long entryAccessRatio = 
                ((cache[entryPos].accessCount * 100) / accessCount);
            if (entryAccessRatio < desiredEntryAccessRatio) {
                toRemove[entriesFound] = entryPos;
                totalSpace += cache[entryPos].size;
                toFree -= cache[entryPos].size;
                entriesFound++;
            }
            attempts++;
        }

        // Now remove the selected entries
        java.util.Arrays.sort(toRemove, 0, entriesFound);
        CacheEntry[] newCache = new CacheEntry[cache.length - entriesFound];
        int pos = 0;
        int n = -1;
        if (entriesFound > 0) {
            n = toRemove[0];
            for (int i = 0; i < cache.length; i++) {
                if (i == n) {
                    if ((pos + 1) < entriesFound) {
                        n = toRemove[pos + 1];
                        pos++;
                    } else {
                        pos++;
                        n = -1;
                    }
                } else {
                    newCache[i - pos] = cache[i];
                }
            }
        }
        cache = newCache;
        cacheSize -= totalSpace;

        return true;

    }


    public CacheEntry lookup(String name) {

        CacheEntry cacheEntry = null;
        CacheEntry[] currentCache = cache;
        accessCount++;
        int pos = find(currentCache, name);
        if ((pos != -1) && (name.equals(currentCache[pos].name))) {
            cacheEntry = currentCache[pos];
        }
        if (cacheEntry == null) {
            try {
                cacheEntry = notFoundCache.get(name);
            } catch (Exception e) {
                // Ignore: the reliability of this lookup is not critical
            }
        }
        if (cacheEntry != null) {
            hitsCount++;
        }
        return cacheEntry;

    }


    public void load(CacheEntry entry) {
        if (entry.exists) {
            if (insertCache(entry)) {
                cacheSize += entry.size;
            }
        } else {
            int sizeIncrement = (notFoundCache.get(entry.name) == null) ? 1 : 0;
            notFoundCache.put(entry.name, entry);
            cacheSize += sizeIncrement;
        }
    }


    public boolean unload(String name) {
        CacheEntry removedEntry = removeCache(name);
        if (removedEntry != null) {
            cacheSize -= removedEntry.size;
            return true;
        } else if (notFoundCache.remove(name) != null) {
            cacheSize--;
            return true;
        }
        return false;
    }


    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     */
    private static final int find(CacheEntry[] map, String name) {

        int a = 0;
        int b = map.length - 1;

        // Special cases: -1 and 0
        if (b == -1) {
            return -1;
        }
        if (name.compareTo(map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }

        int i = 0;
        while (true) {
            i = (b + a) >>> 1;
            int result = name.compareTo(map[i].name);
            if (result > 0) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
            if ((b - a) == 1) {
                int result2 = name.compareTo(map[b].name);
                if (result2 < 0) {
                    return a;
                } else {
                    return b;
                }
            }
        }

    }


    /**
     * Insert into the right place in a sorted MapElement array, and prevent
     * duplicates.
     */
    private final boolean insertCache(CacheEntry newElement) {
        CacheEntry[] oldCache = cache;
        int pos = find(oldCache, newElement.name);
        if ((pos != -1) && (newElement.name.equals(oldCache[pos].name))) {
            return false;
        }
        CacheEntry[] newCache = new CacheEntry[cache.length + 1];
        System.arraycopy(oldCache, 0, newCache, 0, pos + 1);
        newCache[pos + 1] = newElement;
        System.arraycopy
            (oldCache, pos + 1, newCache, pos + 2, oldCache.length - pos - 1);
        cache = newCache;
        return true;
    }


    /**
     * Insert into the right place in a sorted MapElement array.
     */
    private final CacheEntry removeCache(String name) {
        CacheEntry[] oldCache = cache;
        int pos = find(oldCache, name);
        if ((pos != -1) && (name.equals(oldCache[pos].name))) {
            CacheEntry[] newCache = new CacheEntry[cache.length - 1];
            System.arraycopy(oldCache, 0, newCache, 0, pos);
            System.arraycopy(oldCache, pos + 1, newCache, pos, 
                             oldCache.length - pos - 1);
            cache = newCache;
            return oldCache[pos];
        }
        return null;
    }

}
