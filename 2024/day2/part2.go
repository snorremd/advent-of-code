package day2

import (
	"aoc2023/utils"
	"fmt"
	"strconv"
	"strings"
)

func Part2() string {
	// Read the input file
	lines := utils.ReadLines("./day2/input.txt")

	// Keep track of the number of safe reports
	safeReports := 0

	// Iterate over each report (line)
	for _, line := range lines {

		// Split the line on white space
		strNumbers := strings.Fields(line)

		// Make a slice of integers with same length as the number of strings
		// Parse and append the integers to the slice
		numbers := make([]int, 0, len(strNumbers))
		for _, s := range strNumbers {
			i, err := strconv.Atoi(s)
			if err != nil {
				fmt.Println("Error converting string to int:", err)
				continue
			}
			numbers = append(numbers, i)
		}

		// Check if removing one level makes it safe
		for skipIndex := 0; skipIndex < len(numbers); skipIndex++ {
			if isSafeWithSkip(numbers, skipIndex) {
				safeReports++
				break
			}
		}
	}

	// Print the result
	return strconv.Itoa(safeReports)
}

// Helper function to check if a sequence is safe with one skipped level
// Essentially the same as the function in part1.go
// Some index magic is done to skip each number in the sequence
func isSafeWithSkip(numbers []int, skipIndex int) bool {
	increasing := true
	decreasing := true

	for i := 1; i < len(numbers); i++ {
		// Skip the number at skipIndex
		if i == skipIndex {
			continue
		}

		// Can't compare with the skipped index
		prevIndex := i - 1
		if prevIndex == skipIndex {
			prevIndex-- // Adjust for the skipped index
		}

		// If the previous index is out of bounds, go to the next iteration
		if prevIndex < 0 {
			continue
		}

		// Check if the difference is at least 1 and at most 3
		diff := utils.AbsInt(numbers[i] - numbers[prevIndex])
		if diff < 1 || diff > 3 {
			return false
		}

		// Check if the numbers are increasing or decreasing
		if numbers[i] > numbers[prevIndex] {
			decreasing = false
		}
		if numbers[i] < numbers[prevIndex] {
			increasing = false
		}
	}

	// If we get here numbers are safe, granted that they are either increasing or decreasing
	return increasing || decreasing
}
