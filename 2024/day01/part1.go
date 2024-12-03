package day01

import (
	"aoc2023/utils"
	"math"
	"sort"
	"strconv"
)

func Part1() string {
	// Read the input file
	lines := utils.ReadLines("./day01/input.txt")

	// Create two lists to store the left and right numbers
	leftList := make([]int, 0, len(lines))
	rightList := make([]int, 0, len(lines))

	// Parse each line into two numbers
	// Parse each line into two numbers and append to respective lists
	for _, line := range lines {
		if len(line) < 13 {
			continue
		}

		num1, err1 := strconv.Atoi(line[:5])
		num2, err2 := strconv.Atoi(line[8:13])

		if err1 != nil || err2 != nil {
			continue
		}

		leftList = append(leftList, num1)
		rightList = append(rightList, num2)
	}

	// To compare lowest, second lowest, etc we need to sort both lists
	sort.Ints(leftList)
	sort.Ints(rightList)

	// Sum absolute differences between the pairs to find solution
	difference := 0
	for i := 0; i < len(leftList); i++ {
		difference += int(math.Abs(float64(leftList[i] - rightList[i])))
	}

	// Print the result
	return strconv.Itoa(difference)
}
