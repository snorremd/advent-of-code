# Advent of Code 2024 - Go edition

This year I decided to try my hands at the Advent of Code challenges using Go.
I've been using Go on and off for some simple projects.
Advent of Code is a good way to get some practice, and hopefully learn some new tricks.

## Running the code

The go code is structured around a main.go file which contains commands to run solutions and benchmarks.
Each day has its own package `dayX` with two files: `part1.go` and `part2.go`.
Each day problem and part solutions are added to a list in the `main.go` file to be run with the command line.

To run a solution, use the following command:

```bash
go run main.go solution --day X --part Y
```

To run benchmarking for a solution, use the following command:

```bash
go run main.go benchmark --day X --part Y
```

## Benchmarks

These benchmarks are mostly for my own curiosity and is run on my own machine, a MacBook Pro with an M1 chip.
Thus they are not comparable to other AoC benchmarks.
These serve to help me optimize my solutions and learn more about Go.

Run a single benchmark with the following command:

```bash
go run main.go benchmark --day X --part Y
```

Run all benchmarks and update the table below with the results:

```bash
go run main.go update-readme-benchmarks
```

<!-- benchmarks -->

| Day | Part 1 | Part 2 | Stars |
| --- | ------ | ------ | ----- |
| 1 | [111µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day1/part1.go) | [150µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day1/part2.go) | ⭐⭐ |

<!-- /benchmarks -->