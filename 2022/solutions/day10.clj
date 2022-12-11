(ns day10
  (:require [clojure.string :as string]))

(def sample-input
  "noop
addx 3
addx -5")

(def input (slurp "input/day10.txt"))
(def sample-input-larger (slurp "input/day10-sample.txt"))

(defn execute-instruction
  [cycles instruction]
  (let [[c x] (last cycles)]
    (cond (= instruction "noop")
          (conj cycles [(inc c) x])

          (re-find #"addx -?\d*" instruction)
          (conj cycles
                [(inc c) x]
                [(+ 2 c) (+ x (-> instruction
                                  (string/split #" ")
                                  second
                                  read-string))])
          :else cycles)))

(as-> input $
  (string/split-lines $)
  (reduce execute-instruction [[1 1]] $)
  (map #(apply * %) $)
  (map (partial nth $) (range 19 240 40))
  (apply + $)
  (println "Part 1: " $))

(as-> input $
  (string/split-lines $)
  (reduce execute-instruction [[1 1]] $)
  (map (fn [[x c]] (>= (inc x) (mod (dec c) 40) (dec x))) $)
  (partition 40 $)
  (map (partial map #(if % "#" ".")) $)
  (map string/join $)
  (string/join "\n" $)
  (do (println "Part 2:")
      (print $)))