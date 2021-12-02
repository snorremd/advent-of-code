(ns day2
  (:require [clojure.string :as string]))

(def sample-input
  "forward 5
down 5
forward 8
up 3
down 8
forward 2")

(def input
  (slurp "input/day2.txt"))


;; Task 1
(->> input
     string/split-lines
     (map #(-> %
               (string/split #" ")
               (update 1 read-string)))
     (reduce (fn [[horizontal depth] [direction distance]]
               (case direction
                 "forward" [(+ horizontal distance) depth]
                 "up" [horizontal (- depth distance)]
                 "down" [horizontal (+ depth distance)]))
             [0 0])
     (apply *))


;; Task 2
(->> input
     string/split-lines
     (map #(-> %
               (string/split #" ")
               (update 1 read-string)))
     (reduce (fn [[horizontal depth aim] [direction distance]]
               (case direction
                 "forward" [(+ horizontal distance) (+ depth (* aim distance)) aim]
                 "up" [horizontal depth (- aim distance)]
                 "down" [horizontal depth (+ aim distance)]))
             [0 0 0])
     (butlast)
     (apply *))