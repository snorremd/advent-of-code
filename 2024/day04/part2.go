package day04

import (
	"fmt"
	"os"
	"strings"
)

func Part2() string {
	// Load string from file
	words, err := os.ReadFile("./day04/input.txt")
	if err != nil {
		fmt.Println("Error reading file:", err)
		return ""
	}

	// In part 2 we are looking for MAS in an X shape
	// We can define the two diagonals of the X

	diagonalToRight := [3][2]int{
		{-1, -1}, // Up left
		{0, 0},   // Current position
		{1, 1},   // Down right
	}

	diagonalToRightReverse := [3][2]int{
		{1, 1},   // Down right
		{0, 0},   // Current position
		{-1, -1}, // Up left
	}

	diagonalToLeft := [3][2]int{
		{-1, 1}, // Up right
		{0, 0},  // Current position
		{1, -1}, // Down left
	}

	diagonalToLeftReverse := [3][2]int{
		{1, -1}, // Down left
		{0, 0},  // Current position
		{-1, 1}, // Up right
	}

	// Words are contiguous and each row is separated by a newline
	// We want to find the width of the rows so we can simulate a 2D array
	// We can do this by finding the first newline character
	rowWidth := strings.IndexByte(string(words), '\n')
	// Height is then the number of rows
	height := strings.Count(string(words), "\n") + 1

	// Simpler to simulate a 2D array without extra fake column
	wordsTrimmed := strings.ReplaceAll(string(words), "\n", "")

	// If the row width is -1, the file is empty
	if rowWidth == -1 {
		return "0"
	}

	// Initialize XMAS count of MAS in X shape
	xmasCount := 0

	// Loop through each character in the file
	// It can't be in the first or last row or column
	// So we skip those
	for i := rowWidth + 1; i < len(wordsTrimmed)-rowWidth-1; i++ {
		col := i % rowWidth
		row := i / rowWidth

		if col == 0 || col == rowWidth-1 || row == 0 || row == height-1 {
			continue
		}

		// Check if the character is an A, if not skip
		if wordsTrimmed[i] != 'A' {
			continue
		}

		// Match toRight diagonal?
		matchesToRight := checkDiagonal(wordsTrimmed, rowWidth, height, row, col, diagonalToRight)
		matchesToRightReverse := checkDiagonal(wordsTrimmed, rowWidth, height, row, col, diagonalToRightReverse)

		// Match toLeft diagonal?
		matchesToLeft := checkDiagonal(wordsTrimmed, rowWidth, height, row, col, diagonalToLeft)
		matchesToLeftReverse := checkDiagonal(wordsTrimmed, rowWidth, height, row, col, diagonalToLeftReverse)

		// Loop through each direction to check for XMAS
		if (matchesToRight || matchesToRightReverse) && (matchesToLeft || matchesToLeftReverse) {
			xmasCount++
		}
	}

	return fmt.Sprintf("%d", xmasCount)
}

func checkDiagonal(wordsTrimmed string, rowWidth int, height int, row, col int, dir [3][2]int) bool {
	for j := 0; j < 3; j++ {
		newRow := row + dir[j][0]
		newCol := col + dir[j][1]

		// Check if we are out of bounds
		if newRow < 0 || newRow >= height || newCol < 0 || newCol >= rowWidth {
			return false
		}

		// Check if character matches corresponding character in MAS
		if wordsTrimmed[newRow*rowWidth+newCol] != "MAS"[j] {
			return false
		}
	}

	return true
}
