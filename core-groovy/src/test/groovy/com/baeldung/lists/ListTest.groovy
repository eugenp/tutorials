package com.baeldung.groovy.lists

import static groovy.test.GroovyAssert.*
import org.junit.Test

class ListTest{

    @Test
    void testCreateList() {

        def list = [1, 2, 3]
        assertNotNull(list)

        def listMix = ['A', "b", 1, true]
        assertTrue(listMix == ['A', "b", 1, true])

        def linkedList = [1, 2, 3] as LinkedList
        assertTrue(linkedList instanceof LinkedList)

        ArrayList arrList = [1, 2, 3]
        assertTrue(arrList.class == ArrayList)

        def list2 = new ArrayList(arrList)
        assertTrue(list2 == arrList)

        def list3 = arrList.clone()
        assertTrue(list3 == arrList)
    }

    @Test
    void testCreateEmptyList() {

        def emptyList = []
        assertTrue(emptyList.size() == 0)
    }

    @Test
    void testCompareTwoLists() {

        def list1 = [5, 6.0, 'p']
        def list2 = [5, 6.0, 'p']
        assertTrue(list1 == list2)
    }

    @Test
    void testGetItemsFromList(){

        def list = ["Hello", "World"]

        assertTrue(list.get(1) == "World")
        assertTrue(list[1] == "World")
        assertTrue(list[-1] == "World")
        assertTrue(list.getAt(1) == "World")
        assertTrue(list.getAt(-2) == "Hello")
    }

    @Test
    void testAddItemsToList() {

        def list = []

        list << 1
        list.add("Apple")
        assertTrue(list == [1, "Apple"])

        list[2] = "Box"
        list[4] = true
        assertTrue(list == [1, "Apple", "Box", null, true])

        list.add(1, 6.0)
        assertTrue(list == [1, 6.0, "Apple", "Box", null, true])

        def list2 = [1, 2]
        list += list2
        list += 12
        assertTrue(list == [1, 6.0, "Apple", "Box", null, true, 1, 2, 12])
    }

    @Test
    void testUpdateItemsInList() {

        def list =[1, "Apple", 80, "App"]
        list[1] = "Box"
        list.set(2,90)
        assertTrue(list == [1, "Box", 90, "App"])
    }

    @Test
    void testRemoveItemsFromList(){

        def list = [1, 2, 3, 4, 5, 5, 6, 6, 7]

        list.remove(3)
        assertTrue(list == [1, 2, 3, 5, 5, 6, 6, 7])

        list.removeElement(5)
        assertTrue(list == [1, 2, 3, 5, 6, 6, 7])

        assertTrue(list - 6 == [1, 2, 3, 5, 7])
    }

    @Test
    void testIteratingOnAList(){

        def list = [1, "App", 3, 4]
        list.each{ println it * 2}

        list.eachWithIndex{ it, i -> println "$i : $it" }
    }

    @Test
    void testCollectingToAnotherList(){

        def list = ["Kay", "Henry", "Justin", "Tom"]
        assertTrue(list.collect{"Hi " + it} == ["Hi Kay", "Hi Henry", "Hi Justin", "Hi Tom"])
    }

    @Test
    void testJoinItemsInAList(){
        assertTrue(["One", "Two", "Three"].join(",") == "One,Two,Three")
    }

    @Test
    void testFilteringOnLists(){
        def list = [2, 1, 3, 4, 5, 6, 76]

        assertTrue(list.find{it > 3} == 4)

        assertTrue(list.findAll{it > 3} == [4, 5, 6, 76])

        assertTrue(list.findAll{ it instanceof Number} == [2, 1, 3, 4, 5, 6, 76])

        assertFalse(list.every{ it < 6})

        assertTrue(list.any{ it%2 == 0})

        assertTrue(list.grep( Number )== [2, 1, 3, 4, 5, 6, 76])

        assertTrue(list.grep{ it> 6 }== [76])
    }

    @Test
    void testGetUniqueItemsInAList(){
        assertTrue([1, 3, 3, 4].toUnique() == [1, 3, 4])

        def list = [1, 3, 3, 4]
        list.unique()
        assertTrue(list == [1, 3, 4])

        assertTrue(["A", "B", "Ba", "Bat", "Cat"].toUnique{ it.size()} == ["A", "Ba", "Bat"])
    }

    @Test
    void testSorting(){

        assertTrue([1, 2, 1, 0].sort() == [0, 1, 1, 2])
        Comparator mc = {a,b -> a == b? 0: a < b? 1 : -1}

        def list = [1, 2, 1, 0]
        list.sort(mc)
        assertTrue(list == [2, 1, 1, 0])

        def list1 = ["na", "ppp", "as"]
        assertTrue(list1.max() == "ppp")
        
        Comparator minc = {a,b -> a == b? 0: a < b? -1 : 1}
        def list2 = [3, 2, 0, 7]
        assertTrue(list2.min(minc) == 0)
    }
}