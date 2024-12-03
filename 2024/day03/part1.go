package day03

import (
	"fmt"
	"os"
	"strconv"
)

func Part1() string {
	// Read the input file
	program, err := os.ReadFile("./day03/input.txt")
	if err != nil {
		fmt.Println("Error reading file:", err)
		return ""
	}

	// Convert into one big string
	programStr := string(program)

	sum := 0

	// A mul instruction is minimum 8 characters long (mul(1,2))
	for i := 0; i < len(programStr)-7; i++ {

		// If we're not at the start of a mul instruction, continue
		if programStr[i:i+4] != "mul(" {
			continue
		}

		// Parse first number
		num1, len1, ok := parseNumber(programStr, i+4)
		if !ok {
			continue
		}

		// Check for comma
		if programStr[i+4+len1] != ',' {
			continue
		}

		// Parse second number
		num2, len2, ok := parseNumber(programStr, i+4+len1+1)
		if !ok {
			continue
		}

		// Check for closing parenthesis
		if programStr[i+4+len1+1+len2] != ')' {
			continue
		}

		// Increment sum
		sum += num1 * num2

		// Skip to the end of the instruction
		i += 4 + len1 + 1 + len2
	}

	return strconv.Itoa(sum)

}

// Given string and index, parse a number from the string starting at the index
// Returns the number, the length, and a boolean indicating if the parse was successful
func parseNumber(program string, index int) (int, int, bool) {
	digits := ""
	for i := index; i < len(program); i++ {
		if program[i] < '0' || program[i] > '9' {
			break
		}
		digits += string(program[i])
	}

	if digits == "" {
		return 0, 0, false
	}

	number, err := strconv.Atoi(digits)

	if err != nil {
		return 0, 0, false
	}

	return number, len(digits), true
}
