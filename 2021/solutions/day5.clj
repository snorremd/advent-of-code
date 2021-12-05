(ns day5
  (:require [clojure.string :as string]))

(def sample-input
  "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2")

(def input
  (slurp "input/day5.txt"))

(take 1 (string/split-lines input))

(def parse-int #(Integer/parseInt %))

(defn points->line-segments
  "Returns horizontal and vertical line segments"
  [diagonals? [x1 y1 x2 y2]]
  (let [[y1' y2'] (sort [y1 y2])
        [x1' x2'] (sort [x1 x2])]
    (cond
      (= x1 x2)
      (map  vector (repeat x1) (range y1' (inc y2')))
      (= y1 y2)
      (map vector (range x1' (inc x2')) (repeat y1))
      diagonals?
      (map vector
           (if (< x1 x2)
             (range x1 (inc x2))
             (range x1 (dec x2) -1))
           (if (< y1 y2)
             (range y1 (inc y2))
             (range y1 (dec y2) -1))))))

(defn solution
  [f input]
  (as-> input $
    (string/split-lines $)
    (map #(string/split % #" -> ") $)
    (flatten $) ;; Simpler to work with flattened sequences
    (map #(string/split % #",") $)
    (flatten $)
    (map parse-int $) ;; Convert flat list of strings to list of integers
    (partition 4 $)
    (map f $)
    (filter (comp not nil?) $)
    (apply concat $)
    (frequencies $)
    (filter #(-> % val (>= 2)) $)
    (count $)))

;; Task 1

(->> (solution (partial points->line-segments false) input)
     (println "Part 1:"))

;; Task 2

(->> (solution (partial points->line-segments true) input)
     (println "Part 2:"))


;; Rich comments
(comment
  (points->line-segments false [0 0 0 8]) ;; Vertical line x1 = 0 y1 = 0 x2 = 0 y2 = 8
  (points->line-segments false [0 0 8 0]) ;; Horizontal line x1 = 0 y1 = 0 x2 = 8 y2 = 0
  (points->line-segments false [8 0 0 0]) ;; Horizontal line x1 = 8 y1 = 0 x2 = 0 y2 = 0
  (points->line-segments false [0 8 0 0]) ;; Vertical line x1 = 0 y1 = 8 x2 = 0 y2 = 0

  ;; Diagonal line
  (points->line-segments true [6 4 2 0]) ;; Diagonal line x1 = 6 y1 = 4 x2 = 2 y2 = 0

  ;; Of course if you want a range counting down you need to supply a negative step value
  (range 6 2 -1)

  )