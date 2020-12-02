(ns day1
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.math.combinatorics :as combo]))

;; Read input and divide into sequence of numbers
(def numbers (->> "./input/day1.txt"
                  slurp
                  string/split-lines
                  (map edn/read-string)))
;; Part one
(->> (combo/combinations numbers 2)
     (filter #(= (apply + %) 2020))
     first
     (apply *))

;; Part two
(->> (combo/combinations numbers 3)
     (filter #(= (apply + %) 2020))
     first
     (apply *))


;; Alternatively use for function to create pairs without using external dep
(->> (for [a numbers
           b numbers
           :when (= 2020 (+ a b))]
       [a b])
     first
     (apply *))