package com.exercises.activeedgetask.exercise1;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SmallestNonOccurringInteger {

    /**
     * Finds the smallest positive integer that does not occur in the array.
     *
     * @param array the input array of integers
     * @return the smallest non-occurring positive integer
     */
    public static int findSmallestNonOccurringInteger(int[] array) {
        Set<Integer> numbers = new HashSet<>();

        for (int number : array) {
            if (number > 0) {
                numbers.add(number);
            }
        }

        int smallestNonOccurring = 1;
        while (numbers.contains(smallestNonOccurring)) {
            smallestNonOccurring++;
        }

        return smallestNonOccurring;
    }


    public static void main(String[] args) {
        int[] array1 = {1, 3, 6, 4, 1, 2};
        int[] array2 = {5, -1, -3};

        log.info("Smallest missing integer in array1: {}", findSmallestNonOccurringInteger(array1));
        log.info("Smallest missing integer in array2: {}", findSmallestNonOccurringInteger(array2));
    }
}
