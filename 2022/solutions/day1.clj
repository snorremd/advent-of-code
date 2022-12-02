(ns day2
  (:require [clojure.string :as string]))

(def sample-input
  "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(def input (slurp "input/day1.txt"))


(as-> input $
  (string/split $ #"\n\n")
  (map (comp #(reduce + %)
             #(map read-string %)
             string/split-lines) $)
  (apply max $)
  (println "Part 1:" $))


(as-> input $
  (string/split $ #"\n\n")
  (map (comp #(reduce + %)
             #(map read-string %)
             string/split-lines) $)
  (->> $ sort reverse (take 3) (apply +))
  (println "Part 2:" $))