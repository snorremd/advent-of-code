(ns day3
  (:require [clojure.string :as string]
            [clojure.set :refer [intersection]]))

(def sample-input
  "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(def input (slurp "input/day3.txt"))

(def scores {\a 1 \b 2 \c 3 \d 4 \e 5 \f 6 \g 7 \h 8 \i 9 \j 10 \k 11 \l 12 \m 13 \n 14 \o 15 \p 16 \q 17 \r 18 \s 19 \t 20 \u 21 \v 22 \w 23 \x 24 \y 25 \z 26
             \A 27 \B 28 \C 29 \D 30 \E 31 \F 32 \G 33 \H 34 \I 35 \J 36 \K 37 \L 38 \M 39 \N 40 \O 41 \P 42 \Q 43 \R 44 \S 45 \T 46 \U 47 \V 48 \W 49 \X 50 \Y 51 \Z 52})

(as-> input $
  (string/split $ #"\n")
  (map #(as-> % $2
          (partition (/ (count $2) 2) $2)
          (intersection (set (first $2)) (set (second $2)))
          (vec $2)) $)
  (flatten $)
  (map scores $)
  (apply + $)
  (println "Part 1:" $))

(as-> input $
  (string/split $ #"\s")
  (map (comp vec set) $)
  (partition 3 $)
  (map #(->> (apply concat %)
             frequencies
             (sort-by second)
             last key) $)
  (map scores $)
  (apply + $)
  (println "Part 2:" $))