(ns day4
  (:require [clojure.string :as string]))

(def sample-input
  "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(def input (slurp "input/day4.txt"))

(as-> input $
  (string/split $ #"[\n,-]")
  (map read-string $)
  (partition 4 $)
  (filter (fn [[x1 x2 y1 y2]]
            (or (and (>= x1 y1) (<= x2 y2)) 
                (and (>= y1 x1) (<= y2 x2)))) $)
  (count $) 
  (println "Part 1:" $))

(as-> input $
  (string/split $ #"[\n,-]")
  (map read-string $)
  (partition 4 $)
  (filter (fn [[x1 x2 y1 y2]]
            (and (<= x1 y2)
                 (<= y1 x2))) $)
  (count $)
  (println "Part 2:" $))