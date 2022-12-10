(ns day9
  (:require [clojure.string :as string]))

(def input (slurp "input/day9.txt"))

(def sample-input
  "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(defn parse-input [i]
  (->> (string/split-lines i)
       (map #(->> (string/split % #" ")
                  (map (fn [fn x] (fn x)) [identity read-string])
                  reverse
                  (apply repeat)))
       flatten))

(def move-head
  {"R" [1 0]
   "U" [0 1]
   "L" [-1 0]
   "D" [0 -1]})

(defn adjacent? [h t]
  (every? #(< (abs %) 2) (map - h t)))

(defn move
  [rope knot]
  (conj rope
        (if (adjacent? (last rope) knot)
          knot ;; No need to move here
          (map (fn [p p-diff]
                 (cond (> p-diff 0) (inc p)
                       (< p-diff 0) (dec p)
                       :else p))
               knot
               (map - (last rope) knot)))))

(defn next-state [{:keys [rope visited] :as acc} dir]
  (as-> acc $
    (assoc $ :rope (reduce move
                           [(map + (first rope) (move-head dir))]
                           (rest rope)))
    (assoc $ :visited (conj visited (last (:rope $))))))

(as-> input $
  (parse-input $)
  (reduce next-state {:rope (repeat 2 [0 0]) :visited #{}} $)
  (:visited $)
  (count $)
  (println "Part 1: " $))

(as-> input $
  (parse-input $)
  (reduce next-state {:rope (repeat 10 [0 0]) :visited #{}} $)
  (:visited $)
  (count $)
  (println "Part 2: " $))