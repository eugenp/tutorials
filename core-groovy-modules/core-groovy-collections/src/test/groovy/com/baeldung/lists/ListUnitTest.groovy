package com.baeldung.lists

import spock.lang.Specification

class ListUnitTest extends Specification {

    def "testCreateList"() {
        when:
        def list = [1, 2, 3]
        def listMix = ['A', "b", 1, true]
        def linkedList = [1, 2, 3] as LinkedList
        ArrayList arrList = [1, 2, 3]
        def copyList = new ArrayList(arrList)
        def cloneList = arrList.clone()

        then:
        list
        listMix == ['A', "b", 1, true]
        linkedList instanceof LinkedList
        arrList.class == ArrayList
        copyList == arrList
        cloneList == arrList
    }

    def "testCreateEmptyList"() {
        when:
        def emptyList = []

        then:
        emptyList.isEmpty()
    }

    def "testCompareTwoLists"() {
        when:
        def list1 = [5, 6.0, 'p']
        def list2 = [5, 6.0, 'p']

        then:
        list1 == list2
    }

    def "testGetItemsFromList"() {
        when:
        def list = ["Hello", "World"]

        then:
        list.get(1) == "World"
        list[1] == "World"
        list[-1] == "World"
        list.getAt(1) == "World"
        list.getAt(-2) == "Hello"
    }

    def "testAddItemsToList"() {
        given:
        def list1 = []
        def list2 = []
        def list3 = [1, 2]

        when:
        list1 << 1 // [1]
        list1.add("Apple") // [1, "Apple"]

        list2[2] = "Box"   // [null, "Box"]
        list2[4] = true    // [null, "Box", null, true]

        list1.add(1, 6.0) // [1, 6.0, "Apple"] 
        list1 += list3                    // [1, 6.0, "Apple", 1, 2]
        list1 += 12                       // [1, 6.0, "Apple", 1, 2, 12]

        then:
        list1 == [1, 6.0, "Apple", 1, 2, 12]
        list2 == [null, null, "Box", null, true]
    }

    def "testUpdateItemsInList"() {
        given:
        def list = [1, "Apple", 80, "App"]

        when:
        list[1] = "Box"
        list.set(2, 90)

        then:
        list == [1, "Box", 90, "App"]
    }

    def "testRemoveItemsFromList"() {
        given:
        def list = [1, 2, 3, 4, 5, 5, 6, 6, 7]

        when:
        list.remove(3) // [1, 2, 3, 5, 5, 6, 6, 7]
        list.removeElement(5) // [1, 2, 3, 5, 6, 6, 7]
        list = list - 6       // [1, 2, 3, 5, 7]

        then:
        list == [1, 2, 3, 5, 7]
    }

    def "testIteratingOnAList"() {
        given:
        def list = [1, "App", 3, 4]

        expect:
        list.each { println it * 2 }
        list.eachWithIndex { it, i -> println "$i : $it" }
    }

    def "testCollectingToAnotherList"() {
        given:
        def list = ["Kay", "Henry", "Justin", "Tom"]

        when:
        def collect = list.collect { "Hi " + it }

        then:
        collect == ["Hi Kay", "Hi Henry", "Hi Justin", "Hi Tom"]
    }

    def "testJoinItemsInAList"() {
        expect:
        ["One", "Two", "Three"].join(",") == "One,Two,Three"
    }

    def "testFilteringOnLists"() {
        given:
        def filterList = [2, 1, 3, 4, 5, 6, 76]
        def conditionList = [2, 1, 3, 4, 5, 6, 76]

        expect:
        filterList.find { it > 3 } == 4
        filterList.findAll { it > 3 } == [4, 5, 6, 76]
        filterList.findAll { it instanceof Number } == [2, 1, 3, 4, 5, 6, 76]
        filterList.grep(Number) == [2, 1, 3, 4, 5, 6, 76]
        filterList.grep { it > 6 } == [76]

        !(conditionList.every { it < 6 })
        conditionList.any { it % 2 == 0 }
    }

    def "testGetUniqueItemsInAList"() {
        given:
        def uniqueList = [1, 3, 3, 4]

        when:
        uniqueList.unique(true)

        then:
        [1, 3, 3, 4].toUnique() == [1, 3, 4]
        uniqueList == [1, 3, 4]
        ["A", "B", "Ba", "Bat", "Cat"].toUnique { it.size() } == ["A", "Ba", "Bat"]
    }

    def "testSorting"() {
        given:
        Comparator naturalOrder = { a, b -> a == b ? 0 : a < b ? -1 : 1 }
        def list = [1, 2, 1, 0]
        def strList = ["na", "ppp", "as"]
        def numberList = [3, 2, 0, 7]

        when:
        list.sort(naturalOrder.reversed())

        then:
        list == [2, 1, 1, 0]
        strList.max() == "ppp"
        [1, 2, 1, 0].sort() == [0, 1, 1, 2]
        numberList.min(naturalOrder) == 0
    }
}
