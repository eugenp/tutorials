package com.baeldung.datastructures

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NodeTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * Test suit for finding the node by value
     * Partition the tests as follows:
     * 1. tree depth: 0, 1, > 1
     * 2. pivot depth location: not present, 0, 1, 2, > 2
     */

    /**
     * Find the node by value
     * 1. tree depth: 0
     * 2. pivot depth location: not present
     */
    @Test
    fun givenDepthZero_whenPivotNotPresent_thenNull() {
        val n = Node(1)
        assertNull(n.find(2))
    }

    /**
     * Find the node by value
     * 1. tree depth: 0
     * 2. pivot depth location: 0
     */
    @Test
    fun givenDepthZero_whenPivotDepthZero_thenReturnNodeItself() {
        val n = Node(1)
        assertEquals(n, n.find(1))
    }

    /**
     * Find the node by value
     * 1. tree depth: 1
     * 2. pivot depth location: not present
     */
    @Test
    fun givenDepthOne_whenPivotNotPresent_thenNull() {
        val n = Node(1, Node(0))
        assertNull(n.find(2))
    }

    /**
     * Find the node by value
     * 1. tree depth: 1
     * 2. pivot depth location: not present
     */
    @Test
    fun givenDepthOne_whenPivotAtDepthOne_thenSuccess() {
        val n = Node(1, Node(0))
        assertEquals(n.left, n.find(0)
        )
    }

    @Test
    fun givenDepthTwo_whenPivotAtDepth2_then_Success() {
        val left = Node(1, Node(0), Node(2))
        val right = Node(5, Node(4), Node(6))
        val n = Node(3, left, right)
        assertEquals(left.left, n.find(0))
    }


    /**
     * Test suit for inserting a value
     * Partition the test as follows:
     * 1. tree depth: 0, 1, 2, > 2
     * 2. depth to insert: 0, 1, > 1
     * 3. is duplicate: no, yes
     * 4. sub-tree: left, right
     */
    /**
     * Test for inserting a value
     * 1. tree depth: 0
     * 2. depth to insert: 1
     * 3. is duplicate: no
     * 4. sub-tree: right
     */
    @Test
    fun givenTreeDepthZero_whenInsertNoDuplicateToRight_thenAddNode() {
        val n = Node(1)
        n.insert(2)
        assertEquals(1, n.key)
        with(n.right!!) {
            assertEquals(2, key)
            assertNull(left)
            assertNull(right)
        }
        assertNull(n.left)
    }

    /**
     * Test for inserting a value
     * 1. tree depth: 0
     * 2. depth to insert: 1
     * 3. is duplicate: no
     * 4. sub-tree: right
     */
    @Test
    fun givenTreeDepthZero_whenInsertNoDuplicateToLeft_thenSuccess() {
        val n = Node(1)
        n.insert(0)
        assertEquals(1, n.key)
        with(n.left!!) {
            assertEquals(0, key)
            assertNull(left)
            assertNull(right)
        }
        assertNull(n.right)
    }

    /**
     * Test for inserting a value
     * 1. tree depth: 0
     * 2. depth to insert: 1
     * 3. is duplicate: yes
     */
    @Test
    fun givenTreeDepthZero_whenInsertDuplicate_thenSuccess() {
        val n = Node(1)
        n.insert(1)
        assertEquals(1, n.key)
        assertNull(n.right)
        assertNull(n.left)
    }


    /**
     * Test suit for inserting a value
     * Partition the test as follows:
     * 1. tree depth: 0, 1, 2, > 2
     * 2. depth to insert: 0, 1, > 1
     * 3. is duplicate: no, yes
     * 4. sub-tree: left, right
     */
    /**
     * Test for inserting a value
     * 1. tree depth: 1
     * 2. depth to insert: 1
     * 3. is duplicate: no
     * 4. sub-tree: right
     */
    @Test
    fun givenTreeDepthOne_whenInsertNoDuplicateToRight_thenSuccess() {
        val n = Node(10, Node(3))
        n.insert(15)
        assertEquals(10, n.key)
        with(n.right!!) {
            assertEquals(15, key)
            assertNull(left)
            assertNull(right)
        }
        with(n.left!!) {
            assertEquals(3, key)
            assertNull(left)
            assertNull(right)
        }
    }

    /**
     * Test for inserting a value
     * 1. tree depth: 1
     * 2. depth to insert: 1
     * 3. is duplicate: no
     * 4. sub-tree: left
     */
    @Test
    fun givenTreeDepthOne_whenInsertNoDuplicateToLeft_thenAddNode() {
        val n = Node(10, null, Node(15))
        n.insert(3)
        assertEquals(10, n.key)
        with(n.right!!) {
            assertEquals(15, key)
            assertNull(left)
            assertNull(right)
        }
        with(n.left!!) {
            assertEquals(3, key)
            assertNull(left)
            assertNull(right)
        }
    }

    /**
     * Test for inserting a value
     * 1. tree depth: 1
     * 2. depth to insert: 1
     * 3. is duplicate: yes
     */
    @Test
    fun givenTreeDepthOne_whenInsertDuplicate_thenNoChange() {
        val n = Node(10, null, Node(15))
        n.insert(15)
        assertEquals(10, n.key)
        with(n.right!!) {
            assertEquals(15, key)
            assertNull(left)
            assertNull(right)
        }
        assertNull(n.left)
    }

    /**
     * Test suit for removal
     * Partition the input as follows:
     * 1. tree depth: 0, 1, 2, > 2
     * 2. value to delete: absent, present
     * 3. # child nodes: 0, 1, 2
     */
    /**
     * Test for removal value
     * 1. tree depth: 0
     * 2. value to delete: absent
     */
    @Test
    fun givenTreeDepthZero_whenValueAbsent_thenNoChange() {
        val n = Node(1)
        n.delete(0)
        assertEquals(1, n.key)
        assertNull(n.left)
        assertNull(n.right)
    }

    /**
     * Test for removal
     * 1. tree depth: 1
     * 2. value to delete: absent
     */
    @Test
    fun givenTreeDepthOne_whenValueAbsent_thenNoChange() {
        val n = Node(1, Node(0), Node(2))
        n.delete(3)
        assertEquals(1, n.key)
        assertEquals(2, n.right!!.key)
        with(n.left!!) {
            assertEquals(0, key)
            assertNull(left)
            assertNull(right)
        }
        with(n.right!!) {
            assertNull(left)
            assertNull(right)
        }
    }

    /**
     * Test suit for removal
     * 1. tree depth: 1
     * 2. value to delete:  present
     * 3. # child nodes: 0
     */
    @Test
    fun givenTreeDepthOne_whenNodeToDeleteHasNoChildren_thenChangeTree() {
        val n = Node(1, Node(0))
        n.delete(0)
        assertEquals(1, n.key)
        assertNull(n.left)
        assertNull(n.right)
    }

    /**
     * Test suit for removal
     * 1. tree depth: 2
     * 2. value to delete:  present
     * 3. # child nodes: 1
     */
    @Test
    fun givenTreeDepthTwo_whenNodeToDeleteHasOneChild_thenChangeTree() {
        val n = Node(2, Node(0, null, Node(1)), Node(3))
        n.delete(0)
        assertEquals(2, n.key)
        with(n.right!!) {
            assertEquals(3, key)
            assertNull(left)
            assertNull(right)
        }
        with(n.left!!) {
            assertEquals(1, key)
            assertNull(left)
            assertNull(right)
        }
    }

    @Test
    fun givenTreeDepthThree_whenNodeToDeleteHasTwoChildren_thenChangeTree() {
        val l = Node(2, Node(1), Node(5, Node(4), Node(6)))
        val r = Node(10, Node(9), Node(11))
        val n = Node(8, l, r)
        n.delete(8)
        assertEquals(6, n.key)
        with(n.left!!) {
            assertEquals(2, key)
            assertEquals(1, left!!.key)
            assertEquals(5, right!!.key)
            assertEquals(4, right!!.left!!.key)
        }
        with(n.right!!) {
            assertEquals(10, key)
            assertEquals(9, left!!.key)
            assertEquals(11, right!!.key)
        }
    }

}
