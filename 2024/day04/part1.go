package day04

import (
	"fmt"
	"os"
	"strings"
)

func Part1() string {
	// Load string from file
	words, err := os.ReadFile("./day04/input.txt")
	if err != nil {
		fmt.Println("Error reading file:", err)
		return ""
	}

	// Define possible directions to search
	directions := [8][2]int{
		{1, 0},   // Up
		{1, 1},   // Up right
		{0, 1},   // Right
		{-1, 1},  // Down right
		{-1, 0},  // Down
		{-1, -1}, // Down left
		{0, -1},  // Left
		{1, -1},  // Up left
	}

	// Words are contiguous and each row is separate by a newline
	// We want to find the width of the rows so we can simulate a 2D array
	// We can do this by finding the first newline character
	rowWidth := strings.IndexByte(string(words), '\n')
	// Height is then the size of the file divided by the row width
	height := len(words) / rowWidth

	// Simpler to simulate a 2D array without extra fake column
	wordsTrimmed := strings.ReplaceAll(string(words), "\n", "")

	// If the row width is -1, the file is empty
	if rowWidth == -1 {
		return "0"
	}

	// Initialize XMAS count
	xmasCount := 0

	// Loop through each character in the file
	for i := 0; i < len(wordsTrimmed); i++ {
		col := i % rowWidth // +1 to account for newline characters
		row := i / rowWidth

		// Check if the character is an X, if not skip
		if wordsTrimmed[i] != 'X' {
			continue
		}

		// Loop through each direction to check for XMAS
		for _, dir := range directions {
			found := true
			for j := 1; j < 4; j++ {
				newRow := row + dir[0]*j
				newCol := col + dir[1]*j

				// Check if we are out of bounds
				if newRow < 0 || newRow >= height || newCol < 0 || newCol >= rowWidth {
					found = false
					break
				}

				// Check if character matches corresponding character in XMAS
				if wordsTrimmed[newRow*rowWidth+newCol] != "XMAS"[j] {
					found = false
					break
				}
			}

			if found {
				xmasCount++
			}
		}

	}

	return fmt.Sprintf("%d", xmasCount)

}
