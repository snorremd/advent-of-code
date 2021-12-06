(ns day6
  (:require [clojure.string :as string]))

(def sample-input
  "3,4,3,1,2")

(def input
  (slurp "input/day6.txt"))

(def parse-int #(Integer/parseInt %))

;; Logic for the super naive solution

(defn mutate-fish
  [fish]
  (if (zero? fish)
    [6 8] ;; Reset fish to 6 and add an 8
    (dec fish)))

(defn naive-solution
  "Recursively loops over list of fish decreasing their counters by 1
   until the counter is 0. Then adds new fish with counter of 8 and
   resets the current fish's counter to 6. Does this days number of times.
   Works for part 1, but is too slow for part 2. Horribly inefficient."
  [input days]
  (as-> input $
    (string/split $ #",")
    (map parse-int $)
    (loop [fishes $
           day 0]
      (println "Day:" day)
      (if (= day days)
        fishes
        (recur (->> fishes
                    (partition (min 10000 (count fishes))
                               (min 10000 (count fishes))
                               nil)
                    (pmap #(map mutate-fish %)) ;; Age fish down by 1
                    flatten
                    vec) ;; Flatten fish list
               (inc day))))
    (count $)))

;; Logic for the much more performant map based solution

(defn +day!
  "Simulates a single day of the lantern fish swimming about.
   Both the fish having reproduced (state 0) and new fishes
   counting down from state 7 end up in state 6."
  [fishes]
  (assoc! fishes
          0 (fishes 1)
          1 (fishes 2)
          2 (fishes 3)
          3 (fishes 4)
          4 (fishes 5)
          5 (fishes 6)
          6 (+ (fishes 7) (fishes 0))
          7 (fishes 8)
          8 (fishes 0)))

(defn map-solution!
  "Performant map solution where each key corresponds to a state (0 through 8)
   and the corresponding value is the number of fish in that state. Uses transient
   map to speed up computation."
  [input days]
  (as-> input $
    (string/split $ #",")
    (map parse-int $)
    (merge (into {} (map vector (range 9) (repeat 0)))
           (frequencies $))
    (transient $) ;; Make map transient (mutable) for performance
    (loop [fishes $
           day 0]
      (if (= day days)
        fishes
        (recur (+day! fishes)
               (inc day))))
    (persistent! $) ;; Then throw it back into a persistent map again
    (apply + (vals $))))


;; Part 1
(->> (map-solution! sample-input 80)
     (println "Part 1:"))

;; Part 2

(->> (map-solution! input 256)
     (println "Part 2:"))


(comment
  (+day! (transient {0 4 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0})))
  
  
  
  