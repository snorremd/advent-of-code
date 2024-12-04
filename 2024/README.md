# Advent of Code 2024 - Go edition

This year I decided to try my hands at the Advent of Code challenges using Go.
I've been using Go on and off for some simple projects.
Advent of Code is a good way to get some practice, and hopefully learn some new tricks.

<!-- benchmarks -->

| Day | Part 1 | Part 2 | Stars |
| --- | ------ | ------ | ----- |
| 1 | [106µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day1/part1.go) | [157µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day1/part2.go) | ⭐⭐ |
| 2 | [194µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day2/part1.go) | [225µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day2/part2.go) | ⭐⭐ |
| 3 | [168µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day3/part1.go) | [172µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day3/part2.go) | ⭐⭐ |
| 4 | [398µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day4/part1.go) | [305µs](https://github.com/snorremd/advent-of-code/blob/main/2024/day4/part2.go) | ⭐⭐ |

<!-- /benchmarks -->

## Running the code

The go code is structured around a main.go file which contains commands to run solutions and benchmarks.
Each day has its own package `dayX` with two files: `part1.go` and `part2.go`.
Each day problem and part solutions are added to a list in the `main.go` file to be run with the command line.

To run a solution, use the following command:

```bash
go run main.go solution --day X --part Y
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