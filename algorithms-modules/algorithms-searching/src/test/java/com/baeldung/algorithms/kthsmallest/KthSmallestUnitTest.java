package com.baeldung.algorithms.kthsmallest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static com.baeldung.algorithms.kthsmallest.KthSmallest.*;
import static org.junit.jupiter.api.Assertions.*;

class KthSmallestUnitTest {

    @Nested
    class Exceptions {

        @Test
        void when_at_least_one_list_is_null_then_an_exception_is_thrown() {

            Executable executable1 = () -> findKthSmallestElement(1, null, null);
            Executable executable2 = () -> findKthSmallestElement(1, new int[]{2}, null);
            Executable executable3 = () -> findKthSmallestElement(1, null, new int[]{2});

            assertThrows(IllegalArgumentException.class, executable1);
            assertThrows(IllegalArgumentException.class, executable2);
            assertThrows(IllegalArgumentException.class, executable3);
        }

        @Test
        void when_at_least_one_list_is_empty_then_an_exception_is_thrown() {

            Executable executable1 = () -> findKthSmallestElement(1, new int[]{}, new int[]{2});
            Executable executable2 = () -> findKthSmallestElement(1, new int[]{2}, new int[]{});
            Executable executable3 = () -> findKthSmallestElement(1, new int[]{}, new int[]{});

            assertThrows(IllegalArgumentException.class, executable1);
            assertThrows(IllegalArgumentException.class, executable2);
            assertThrows(IllegalArgumentException.class, executable3);
        }

        @Test
        void when_k_is_smaller_than_0_then_an_exception_is_thrown() {
            Executable executable1 = () -> findKthSmallestElement(-1, new int[]{2}, new int[]{2});
            assertThrows(IllegalArgumentException.class, executable1);
        }

        @Test
        void when_k_is_smaller_than_1_then_an_exception_is_thrown() {
            Executable executable1 = () -> findKthSmallestElement(0, new int[]{2}, new int[]{2});
            assertThrows(IllegalArgumentException.class, executable1);
        }

        @Test
        void when_k_bigger_then_the_two_lists_then_an_exception_is_thrown() {
            Executable executable1 = () -> findKthSmallestElement(6, new int[]{1, 5, 6}, new int[]{2, 5});
            assertThrows(NoSuchElementException.class, executable1);
        }

    }

    @Nested
    class K_is_smaller_than_the_size_of_list1_and_the_size_of_list2 {

        @Test
        public void when_k_is_1_then_the_smallest_element_is_returned_from_list1() {
            int result = findKthSmallestElement(1, new int[]{2, 7}, new int[]{3, 5});
            assertEquals(2, result);
        }

        @Test
        public void when_k_is_1_then_the_smallest_element_is_returned_list2() {
            int result = findKthSmallestElement(1, new int[]{3, 5}, new int[]{2, 7});
            assertEquals(2, result);
        }

        @Test
        public void when_kth_element_is_smallest_element_and_occurs_in_both_lists() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(1, list1, list2);
            assertEquals(1, result);
        }

        @Test
        public void when_kth_element_is_smallest_element_and_occurs_in_both_lists2() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(2, list1, list2);
            assertEquals(1, result);
        }

        @Test
        public void when_kth_element_is_largest_element_and_occurs_in_both_lists_1() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(5, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void when_kth_element_is_largest_element_and_occurs_in_both_lists_2() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(6, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void when_kth_element_and_occurs_in_both_lists() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{0, 2, 3};
            int result = findKthSmallestElement(3, list1, list2);
            assertEquals(2, result);
        }

        @Test
        public void and_kth_element_is_in_first_list() {
            int[] list1 = new int[]{1,2,3,4};
            int[] list2 = new int[]{1,3,4,5};
            int result = findKthSmallestElement(3,  list1, list2);
            assertEquals(2, result);
        }

        @Test
        public void and_kth_is_in_second_list() {
            int[] list1 = new int[]{1,3,4,4};
            int[] list2 = new int[]{1,2,4,5};
            int result = findKthSmallestElement(3,  list1, list2);
            assertEquals(2, result);
        }

        @Test
        public void and_elements_in_first_list_are_all_smaller_than_second_list() {
            int[] list1 = new int[]{1,3,7,9};
            int[] list2 = new int[]{11,12,14,15};
            int result = findKthSmallestElement(3,  list1, list2);
            assertEquals(7, result);
        }

        @Test
        public void and_elements_in_first_list_are_all_smaller_than_second_list2() {
            int[] list1 = new int[]{1,3,7,9};
            int[] list2 = new int[]{11,12,14,15};
            int result = findKthSmallestElement(4,  list1, list2);
            assertEquals(9, result);
        }

        @Test
        public void and_only_elements_from_second_list_are_part_of_result() {
            int[] list1 = new int[]{11,12,14,15};
            int[] list2 = new int[]{1,3,7,9};
            int result = findKthSmallestElement(3,  list1, list2);
            assertEquals(7, result);
        }

        @Test
        public void and_only_elements_from_second_list_are_part_of_result2() {
            int[] list1 = new int[]{11,12,14,15};
            int[] list2 = new int[]{1,3,7,9};
            int result = findKthSmallestElement(4,  list1, list2);
            assertEquals(9, result);
        }
    }

    @Nested
    class K_is_bigger_than_the_size_of_at_least_one_of_the_lists {

        @Test
        public void k_is_smaller_than_list1_and_bigger_than_list2() {
            int[] list1 = new int[]{1, 2, 3, 4, 7, 9};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(5, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void k_is_bigger_than_list1_and_smaller_than_list2() {
            int[] list1 = new int[]{1, 2, 3};
            int[] list2 = new int[]{1, 2, 3, 4, 7, 9};
            int result = findKthSmallestElement(5, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void when_k_is_bigger_than_the_size_of_both_lists_and_elements_in_second_list_are_all_smaller_than_first_list() {
            int[] list1 = new int[]{9, 11, 13, 55};
            int[] list2 = new int[]{1, 2, 3, 7};
            int result = findKthSmallestElement(6, list1, list2);
            assertEquals(11, result);
        }

        @Test
        public void when_k_is_bigger_than_the_size_of_both_lists_and_elements_in_second_list_are_all_bigger_than_first_list() {
            int[] list1 = new int[]{1, 2, 3, 7};
            int[] list2 = new int[]{9, 11, 13, 55};
            int result = findKthSmallestElement(6, list1, list2);
            assertEquals(11, result);
        }

        @Test
        public void when_k_is_bigger_than_the_size_of_both_lists() {
            int[] list1 = new int[]{3, 7, 9, 11, 55};
            int[] list2 = new int[]{1, 2, 3, 7, 13};
            int result = findKthSmallestElement(7, list1, list2);
            assertEquals(9, result);
        }

        @Test
        public void when_k_is_bigger_than_the_size_of_both_lists_and_list1_has_more_elements_than_list2() {
            int[] list1 = new int[]{3, 7, 9, 11, 55, 77, 100, 200};
            int[] list2 = new int[]{1, 2, 3, 7, 13};
            int result = findKthSmallestElement(11, list1, list2);
            assertEquals(77, result);
        }

        @Test
        public void max_test() {
            int[] list1 = new int[]{100, 200};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(4, list1, list2);
            assertEquals(100, result);
        }

        @Test
        public void max_test2() {
            int[] list1 = new int[]{100, 200};
            int[] list2 = new int[]{1, 2, 3};
            int result = findKthSmallestElement(5, list1, list2);
            assertEquals(200, result);
        }

        @Test
        public void when_k_is_smaller_than_the_size_of_both_lists_and_kth_element_in_list2() {
            int[] list1 = new int[]{1, 2, 5};
            int[] list2 = new int[]{1, 3, 4, 7};
            int result = findKthSmallestElement(4, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void when_k_is_smaller_than_the_size_of_both_lists_and_kth_element_is_smallest_in_list2() {
            int[] list1 = new int[]{1, 2, 5};
            int[] list2 = new int[]{3, 4, 7};
            int result = findKthSmallestElement(3, list1, list2);
            assertEquals(3, result);
        }

        @Test
        public void when_k_is_smaller_than_the_size_of_both_lists_and_kth_element_is_smallest_in_list23() {
            int[] list1 = new int[]{3, 11, 27, 53, 90};
            int[] list2 = new int[]{4, 20, 21, 100};
            int result = findKthSmallestElement(5, list1, list2);
            assertEquals(21, result);
        }
    }

//    @Test
//    public void randomTests() {
//        IntStream.range(1, 100000).forEach(i -> random());
//    }

    private void random() {

        Random random = new Random();
        int length1 = (Math.abs(random.nextInt())) % 1000 + 1;
        int length2 = (Math.abs(random.nextInt())) % 1000 + 1;

        int[] list1 = sortedRandomIntArrayOfLength(length1);
        int[] list2 = sortedRandomIntArrayOfLength(length2);

        int k = (Math.abs(random.nextInt()) % (length1 + length2)) + 1 ;

        int result = findKthSmallestElement(k,  list1, list2);

        int result2 = getKthElementSorted(list1, list2, k);

        int result3 = getKthElementMerge(list1, list2, k);

        assertEquals(result2, result);
        assertEquals(result2, result3);
    }

    private int[] sortedRandomIntArrayOfLength(int length) {
        int[] intArray = new Random().ints(length).toArray();
        Arrays.sort(intArray);
        return intArray;
    }
}