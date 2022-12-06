(ns day5
  (:require [clojure.string :as string]))

(def sample-input
  "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(def input (-> (slurp "input/day5.txt")
               #_sample-input
               string/split-lines))

(defn indices [i] ;; Find the indices of each stack for easy retrieval
  (-> i first count (as-> $ (range 1 (inc $) 4))))

(defn stacks [i]
  (as-> i $
    (take-while #(re-find #"\[" %) $)
    (map #(map get (repeat %) (indices i)) $)
    (apply map vector $) ;; Pivot the seqs so that we get the vertical stacks
    (mapv #(filter (partial not= \space) %) $))) ;; Remove empty spaces from stacks

(defn moves [i]
  (as-> i $
    (drop-while #(not (re-find #"move" %)) $)
    (map #(->> %
               (re-matches #"move (\d+) from (\d+) to (\d+)")
               (drop 1)
               (map read-string)) $)))

(defn solve [input move-fn]
  (->> (reduce (fn [stacks [num from to]]
                 (let [from-stack (nth stacks (dec from))
                       to-stack (nth stacks (dec to))]
                   (-> stacks
                       (assoc (dec from) (take-last (- (count from-stack) num) from-stack))
                       (assoc (dec to) (concat (move-fn (take num from-stack)) to-stack)))))
               (stacks input)
               (moves input))
       (map first)
       string/join))

(->> (solve input reverse)
     (println "Part 1:"))

(->> (solve input identity)
     (println "Part 2:"))
