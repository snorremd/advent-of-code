package day01

import (
	"aoc2023/utils"
	"strconv"
	"strings"
)

func Part2() string {
	// Read the input file
	lines := utils.ReadLines("./day01/input.txt")

	// Create two lists to store the left and right numbers
	leftList := []int{}
	rightList := []int{}

	// Parse each line into two numbers
	for _, line := range lines {
		parts := strings.Fields(line)
		num1, err1 := strconv.Atoi(parts[0])
		num2, err2 := strconv.Atoi(parts[1])

		if err1 != nil || err2 != nil {
			continue
		}

		leftList = append(leftList, num1)
		rightList = append(rightList, num2)
	}

	// Store frequency of each number in the right list
	counts := make(map[int]int, len(rightList))
	for _, num := range rightList {
		counts[num]++
	}

	// Now sum the left numbers, multiplying each by the number of times it appears in the right list
	sum := 0
	for _, num := range leftList {
		sum += num * counts[num]
	}

	// Print the result
	return strconv.Itoa(sum)
}
