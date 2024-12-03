package day03

import (
	"fmt"
	"os"
	"strconv"
)

func Part2() string {
	// Read the input file
	program, err := os.ReadFile("./day03/input.txt")
	if err != nil {
		fmt.Println("Error reading file:", err)
		return ""
	}

	// Convert into one big string
	programStr := string(program)

	sum := 0
	mulEnabled := true

	// A mul instruction is minimum 8 characters long (mul(1,2))
	for i := 0; i < len(programStr)-7; i++ {

		// Check if we have a do() command
		if parseDoCmd(programStr, i) {
			mulEnabled = true
			i += 3
			continue
		}

		// Check if we have a dont() command
		if parseDontCmd(programStr, i) {
			mulEnabled = false
			i += 5
			continue
		}

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
		if mulEnabled {
			sum += num1 * num2
		}

		// Skip to the end of the instruction
		i += 4 + len1 + 1 + len2
	}

	return strconv.Itoa(sum)

}

// Checks if next part of program is a do() command
func parseDoCmd(program string, index int) bool {
	return program[index:index+4] == "do()"
}

// Checks if next part of program is a don't() command
func parseDontCmd(program string, index int) bool {
	return program[index:index+7] == "don't()"
}
