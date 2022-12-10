(ns day8
  (:require [clojure.string :as string]))

(def sample-input
  "30373
25512
65332
33549
35390")

(def input (slurp "input/day8.txt"))

(defn take-until 
  "Borrowed from clojure wiki https://ask.clojure.org/index.php/2777/add-take-until?show=2969#a2969"
  [p s]
  (transduce (halt-when p (fn [r h] (conj r h))) conj [] s))

(defn coords [x1 x2 y1 y2]
  (for [y (range y1 y2)
        x (range x1 x2)]
    [x y]))

(defn treeline
  [grid x y]
  [(mapv #(get-in grid [% y]) (range (dec x) (dec 0) -1)) ;; left
   (mapv #(get-in grid [% y]) (range (inc x) (count grid))) ;; right
   (mapv #(get-in grid [x %]) (range (dec y) (dec 0) -1)) ;; up
   (mapv #(get-in grid [x %]) (range (inc y) (count grid)))]) ;; down

(defn is-visible? [grid x y]
  (some #(> (get-in grid [x y])
            (apply max %))
        (treeline grid x y)))

(defn scenic-score [grid [x y]]
  (->> (treeline grid x y)
       (map #(take-until (partial <= (get-in grid [x y])) %))
       (map count)
       (apply *)))

(defn plant-forest ;; Pivot input to make x and y coordinates work
  [input]
  (->> (string/split input #"\n")
       (mapv #(mapv (comp read-string str) %))
       (apply mapv vector)))

(as-> input $
  (plant-forest $)
  (reduce (fn [acc [x y]]
            (if (is-visible? $ x y) (inc acc) acc))
          (- (* (count $) 4) 4) ;; Start with 4 edges visible
          (coords 1 (-> $ count dec) 1 (-> $ count dec)))
  (println "Part 1:" $))

(as-> input $
  (plant-forest $)
  (mapv (partial scenic-score $)
        (coords 0 (-> $ count) 0 (-> $ count)))
  (apply max $)
  (println "Part 2:" $))