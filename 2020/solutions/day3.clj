(ns day3
  (:require [clojure.string :as string]))

(def input-area
  "Get the input and turn it into lines of strings"
  (->> "./input/day3.txt"
       slurp
       string/split-lines))

(def width
  "Get the width of the area"
  (count (first input-area)))

(defn slope
  "Function that calculates number of trees hit for each kind of
   slope according to slope form and the input area."
  [right down]
  ;; Recursive loop moving down the rows keeping track of trees
  (loop [col 0 row 0 trees 0 area input-area]
  ;; Use modulo to wrap over the existing pattern
    (let [new-col (mod (+ col right) width)
          new-row (+ row down)]
      (if (> new-row (count input-area))
        trees
        (recur new-col
               new-row
               (if (= \# (get-in area [row col]))
                 (inc trees)
                 trees)
               input-area)))))

;; Solution 1
(slope 3 1)

;; Solution 2
(->> [[1 1] [3 1] [5 1] [7 1] [1 2]]
     (map #(apply slope %))
     (reduce *))