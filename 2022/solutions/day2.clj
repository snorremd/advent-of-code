(ns day2
  (:require [clojure.string :as string]))

(def sample-input
  "A Y
B X
C Z")

(def input (slurp "input/day2.txt"))

(def translate ;; Mostly for making things more readable
  {"A" :rock
   "B" :paper
   "C" :scissors
   "X" :rock
   "Y" :paper
   "Z" :scissors})

(def scores-and-strategy
  "Table acts as lookup function for calculating scores and moves"
  {[:rock :rock] [4 :scissors]
   [:rock :paper] [8 :rock]
   [:rock :scissors] [3 :paper]
   [:paper :rock] [1 :rock]
   [:paper :paper] [5 :paper]
   [:paper :scissors] [9 :scissors]
   [:scissors :rock] [7 :paper]
   [:scissors :paper] [2 :scissors]
   [:scissors :scissors] [6 :rock]})

(as-> input $
  (string/split $ #"\s")
  (partition 2 $)
  (map #(mapv translate %) $)
  (map (comp first scores-and-strategy) $)
  (apply + $)
  (println "Part 1:" $))

(as-> input $
  (string/split $ #"\s")
  (partition 2 $)
  (map #(mapv translate %) $)
  (map #(assoc % 1 (-> % scores-and-strategy second)) $)
  (map (comp first scores-and-strategy) $)
  (apply + $)
  (println "Part 2:" $))