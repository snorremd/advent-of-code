package main

import (
	"context"
	"fmt"
	"log"
	"os"

	"aoc2023/day01"
	"aoc2023/day02"
	"aoc2023/day03"
	"aoc2023/day04"
	"aoc2023/utils"

	"github.com/urfave/cli/v3"
)

// Should be nillable to allow for the solution to be nil
var solutions = []utils.Problem{
	{
		Day:   1,
		Name:  "Day 1: Historian Hysteria",
		Part1: day01.Part1,
		Part2: day01.Part2,
	},
	{
		Day:   2,
		Name:  "Day 2: Red-Nosed Reports",
		Part1: day02.Part1,
		Part2: day02.Part2,
	},
	{
		Day:   3,
		Name:  "Day 3: Mull it over",
		Part1: day03.Part1,
		Part2: day03.Part2,
	},
	{
		Day:   4,
		Name:  "Day 4: Ceres Search",
		Part1: day04.Part1,
		Part2: day04.Part2,
	},
}

func main() {

	cmd := &cli.Command{
		Name:  "Advent of Code 2024",
		Usage: "Run solutions for Advent of Code 2024",
		Commands: []*cli.Command{
			{
				Name: "solve",
				Flags: []cli.Flag{
					&cli.StringFlag{
						Name:  "day",
						Value: "",
						Usage: "Day to solve",
					},
					&cli.StringFlag{
						Name:  "part",
						Value: "",
						Usage: "Part to solve",
					},
				},
				Action: func(ctx context.Context, cmd *cli.Command) error {

					selectedProblem := getSolution(cmd)

					if selectedProblem == nil {
						fmt.Printf("Problem %s not found\n", cmd.String("day"))
						os.Exit(1)
					}

					// Finally, run the solution on it's own
					solution := selectedProblem()
					fmt.Println(solution)
					return nil
				},
			},
			{
				Name: "benchmark",
				Flags: []cli.Flag{
					&cli.StringFlag{
						Name:  "day",
						Value: "",
						Usage: "Day to benchmark",
					},
					&cli.StringFlag{
						Name:  "part",
						Value: "",
						Usage: "Part to benchmark",
					},
				},
				Action: func(ctx context.Context, cmd *cli.Command) error {
					var selectedSolution func() string
					solution := fmt.Sprintf("%s:%s", cmd.String("day"), cmd.String("part"))
					selectedSolution = getSolution(cmd)

					if selectedSolution == nil {
						fmt.Printf("Solution %s not found\n", solution)
						os.Exit(1)
					}

					fmt.Println("Result", utils.Benchmark(selectedSolution))

					return nil
				},
			},
			{
				Name: "update-readme-benchmarks",
				Action: func(ctx context.Context, cmd *cli.Command) error {
					utils.UpdateReadmeBenchmarks(solutions)

					return nil
				},
			},
		},
	}

	if err := cmd.Run(context.Background(), os.Args); err != nil {
		log.Fatal(err)
	}

}

func getSolution(cmd *cli.Command) func() string {
	var selectedProblem func() string = nil
	for _, problem := range solutions {
		if cmd.String("day") == fmt.Sprintf("%d", problem.Day) {
			if cmd.String("part") == "1" {
				selectedProblem = problem.Part1
			} else if cmd.String("part") == "2" {
				selectedProblem = problem.Part2
			}
			break
		}
	}
	return selectedProblem
}
