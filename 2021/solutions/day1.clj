(ns day1
  (:require [clojure.string :as string]))

(def sample-input
  "199
200
208
210
200
207
240
269
260
263")

(def input (slurp "input/day1.txt"))

(->> input
     string/split-lines
     (map read-string)
     (partition 2 1)
     (filter #(> (second %) (first %)))
     count)


(->> input
     string/split-lines
     (map read-string)
     (partition 3 1)
     (map #(apply + %))
     (partition 2 1)
     (filter #(> (second %) (first %)))
     count)

;; Teating lazygit