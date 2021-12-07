(ns day7
  (:require [clojure.string :as string]))

(def sample-input "16,1,2,0,4,2,7,1,2,14")
(def input (slurp "input/day7.txt"))

;; Arithmetic progression
(defn progression-cost [n]
  (* (/ n 2) (+ 1 n)))

;; Make proper functions for static Java Methods so they can be used everywhere
(def parse-int #(Integer/parseInt %))
(def abs #(Math/abs %))

(defn move-linear
  [to from]
  (abs (- to from)))

(defn move-progression
  [to from]
  (progression-cost (move-linear to from)))

(defn solution
  [move-fn input]
  (as-> input $
    (string/split $ #",")
    (map parse-int $)
    (assoc {}
           :min (apply min $)
           :max (apply max $)
           :crabs $)
    (for [pos (range (:min $) (inc (:max $)))
          crab (:crabs $)]
      [pos crab (move-fn pos crab)])
    (partition-by first $)
    (map #(map last %) $)
    (map #(reduce + %) $)
    (apply min $)
    (int $)))

;; Part 1

(->> (solution move-linear input)
     (println "Part 1:"))

;; Part 2

(->> (solution move-progression input)
     (println "Part 2:"))


(comment
  (partition-by first [[1 2 3] [1 4 5] [2 3 4] [2 5 6] [3 4 5] [3 6 7]])
  (progression-cost 1)
  (progression-cost 2)
  (progression-cost 11))