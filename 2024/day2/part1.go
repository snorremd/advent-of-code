package day2

import (
	"aoc2023/utils"
	"fmt"
	"strconv"
	"strings"
)

func Part1() string {
	// Read the input file
	lines := utils.ReadLines("./day2/input.txt")

	// Each line is a sequence of numbers denoting reactor report
	// If the numbers are either all increasing or decreasing they are safe granted that
	// the difference is at least 1 and at most 3
	// Count the number of safe reports

	safeReports := 0

	for _, line := range lines {
		// Each line is a white space separated list of numbers
		// Split the line into numbers
		strNumbers := strings.Fields(line)
		numbers := make([]int, 0, len(strNumbers))
		for _, s := range strNumbers {
			i, err := strconv.Atoi(s)
			if err != nil {
				fmt.Println("Error converting string to int:", err)
				continue
			}
			numbers = append(numbers, i)
		}

		// Check if the numbers are all increasing or decreasing
		increasing := true
		decreasing := true
		withinMargin := true

		for i := 1; i < len(numbers); i++ {
			if numbers[i] > numbers[i-1] {
				decreasing = false
			} else if numbers[i] < numbers[i-1] {
				increasing = false
			}

			// Exit early if both conditions are false
			if !increasing && !decreasing {
				break
			}

			// Check if the difference is at least 1 and at most 3
			if utils.AbsInt(numbers[i]-numbers[i-1]) > 3 ||
				utils.AbsInt(numbers[i]-numbers[i-1]) < 1 {
				withinMargin = false
				break
			}
		}

		// If the report is safe, increment the counter
		if (increasing || decreasing) && withinMargin {
			safeReports++
		}
	}

	// Print the result
	return strconv.Itoa(safeReports)
}
