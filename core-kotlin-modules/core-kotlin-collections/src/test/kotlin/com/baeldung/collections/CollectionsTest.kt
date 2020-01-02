package com.baeldung.collections

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CollectionsTest {

    @Test
    fun whenUseDifferentCollections_thenSuccess () {
        val theList = listOf("one", "two", "three")
        assertTrue(theList.contains("two"))     

        val theMutableList = mutableListOf("one", "two", "three")
        theMutableList.add("four")
        assertTrue(theMutableList.contains("four"))                        

        val theSet = setOf("one", "two", "three")
        assertTrue(theSet.contains("three"))    

        val theMutableSet = mutableSetOf("one", "two", "three")
        theMutableSet.add("four")
        assertTrue(theMutableSet.contains("four"))                

        val theMap = mapOf(1 to "one", 2 to "two", 3 to "three")
        assertEquals(theMap[2],"two")  

        val theMutableMap = mutableMapOf(1 to "one", 2 to "two", 3 to "three")
        theMutableMap[4] = "four"
        assertEquals(theMutableMap[4],"four")             
    }

    @Test
    fun whenSliceCollection_thenSuccess () {
        val theList = listOf("one", "two", "three")
        val resultList = theList.slice(1..2)

        assertEquals(2, resultList.size)        
        assertTrue(resultList.contains("two"))        
    }
    
    @Test
    fun whenJoinTwoCollections_thenSuccess () {
        val firstList = listOf("one", "two", "three")
        val secondList = listOf("four", "five", "six")
        val resultList = firstList + secondList

        assertEquals(6, resultList.size)   
        assertTrue(resultList.contains("two"))        
        assertTrue(resultList.contains("five"))        
    }      
    
    @Test
    fun whenFilterNullValues_thenSuccess () {
        val theList = listOf("one", null, "two", null, "three")
        val resultList = theList.filterNotNull()

        assertEquals(3, resultList.size)        
    }    

    @Test
    fun whenFilterNonPositiveValues_thenSuccess () {
        val theList = listOf(1, 2, -3, -4, 5, -6)
        val resultList = theList.filter{ it > 0}
        //val resultList = theList.filter{ x -> x > 0}

        assertEquals(3, resultList.size)  
        assertTrue(resultList.contains(1))
        assertFalse(resultList.contains(-4))      
    }     

    @Test
    fun whenDropFirstItems_thenRemoved () {
        val theList = listOf("one", "two", "three", "four")
        val resultList = theList.drop(2)

        assertEquals(2, resultList.size)        
        assertFalse(resultList.contains("one"))        
        assertFalse(resultList.contains("two"))        
    }  

    @Test
    fun whenDropFirstItemsBasedOnCondition_thenRemoved () {
        val theList = listOf("one", "two", "three", "four")
        val resultList = theList.dropWhile{ it.length < 4 }

        assertEquals(2, resultList.size)        
        assertFalse(resultList.contains("one"))        
        assertFalse(resultList.contains("two"))        
    }      

    @Test
    fun whenExcludeItems_thenRemoved () {
        val firstList = listOf("one", "two", "three")
        val secondList = listOf("one", "three")
        val resultList = firstList - secondList

        assertEquals(1, resultList.size)        
        assertTrue(resultList.contains("two"))        
    }   

    @Test
    fun whenSearchForExistingItem_thenFound () {
        val theList = listOf("one", "two", "three")

        assertTrue("two" in theList)        
    } 

    @Test
    fun whenGroupItems_thenSuccess () {
        val theList = listOf(1, 2, 3, 4, 5, 6)
        val resultMap = theList.groupBy{ it % 3}

        assertEquals(3, resultMap.size)  
        print(resultMap[1])
        assertTrue(resultMap[1]!!.contains(1))
        assertTrue(resultMap[2]!!.contains(5))      
    } 

    @Test
    fun whenApplyFunctionToAllItems_thenSuccess () {
        val theList = listOf(1, 2, 3, 4, 5, 6)
        val resultList = theList.map{ it * it }
        print(resultList)
        assertEquals(4, resultList[1])
        assertEquals(9, resultList[2])
    } 

    @Test
    fun whenApplyMultiOutputFunctionToAllItems_thenSuccess () {
        val theList = listOf("John", "Tom")
        val resultList = theList.flatMap{ it.toLowerCase().toList()}
        print(resultList)
        assertEquals(7, resultList.size)
        assertTrue(resultList.contains('j'))
    }  

    @Test
    fun whenApplyFunctionToAllItemsWithStartingValue_thenSuccess () {
        val theList = listOf(1, 2, 3, 4, 5, 6)
        val  finalResult = theList.fold( 1000, { oldResult, currentItem -> oldResult + (currentItem *currentItem)})
        print(finalResult)
        assertEquals(1091, finalResult)
    }
}