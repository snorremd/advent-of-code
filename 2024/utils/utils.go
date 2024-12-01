package utils

import (
	"bufio"
	"bytes"
	"fmt"
	"os"
	"testing"
)

type Problem struct {
	Day   int
	Name  string
	Part1 func() string
	Part2 func() string
}

func ReadLines(path string) []string {
	file, err := os.Open(path)
	if err != nil {
		fmt.Println("Error opening file:", err)
	}

	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	return lines
}

func Benchmark(solution func() string) string {
	result := testing.Benchmark(func(b *testing.B) {
		for i := 0; i < b.N; i++ {
			solution()
		}
	})

	return fmt.Sprintf("%dµs", result.NsPerOp()/1000)
}

func UpdateReadmeBenchmarks(solutions []Problem) {
	// Read the README file
	readmePath := "./README.md"
	readme, err := os.ReadFile(readmePath)

	if err != nil {
		fmt.Println("Error reading README file:", err)
		return
	}

	startTag := "<!-- benchmarks -->"
	endTag := "<!-- /benchmarks -->"

	// To find the index of the substring
	startIndex := bytes.Index(readme, []byte(startTag))
	endIndex := bytes.Index(readme, []byte(endTag))

	if startIndex == -1 || endIndex == -1 {
		fmt.Println("Could not find benchmarks section in README")
		return
	}

	// Generate the new benchmarks section
	newBenchmarks := startTag + "\n\n"
	newBenchmarks += "| Day | Part 1 | Part 2 | Stars |\n"
	newBenchmarks += "| --- | ------ | ------ | ----- |\n"

	partUrl := "https://github.com/snorremd/advent-of-code/blob/main/2024/day%s/part%s.go"

	// Add the benchmarks for each solution
	for _, solution := range solutions {
		bench1 := ""
		bench2 := ""
		bench1Url := ""
		bench2Url := ""
		stars := ""

		if solution.Part1 != nil {
			bench1 = Benchmark(solution.Part1)
			bench1Url = fmt.Sprintf(partUrl, fmt.Sprintf("%d", solution.Day), "1")
			stars += "⭐"
		} else {
			bench1 = "-"
		}

		if solution.Part2 != nil {
			bench2 = Benchmark(solution.Part2)
			bench2Url = fmt.Sprintf(partUrl, fmt.Sprintf("%d", solution.Day), "2")
			stars += "⭐"
		} else {
			bench2 = "-"
		}

		newBenchmarks += fmt.Sprintf(
			"| %d | [%s](%s) | [%s](%s) | %s |\n",
			solution.Day,
			bench1,
			bench1Url,
			bench2,
			bench2Url,
			stars,
		)
	}
	newBenchmarks += "\n" + endTag

	// Replace the old benchmarks section with the new one
	newReadme := bytes.ReplaceAll(readme, readme[startIndex:endIndex+len(endTag)], []byte(newBenchmarks))

	// Write the updated README file
	err = os.WriteFile(readmePath, newReadme, 0644)
}
