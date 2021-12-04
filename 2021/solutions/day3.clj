(ns day3
  (:require [clojure.string :as string]))

(def sample-input
  "00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010")

(def input (slurp "input/day3.txt"))

;; Part 1

(->> input
     string/split-lines
     (apply (partial map (fn [& xs] xs))) ;; Pivot
     (map frequencies)
     (map (juxt #(key (apply max-key val %))
                #(key (apply min-key val %))))
     (apply map vector) ;; Split into vectors of most common and least common
     (map (comp #(Integer/parseInt % 2) (partial string/join ""))) ;; Turn into numbers
     (apply *))

;; Part 2

(defn max-key-fn
  "Given frequencies of each digit returns the most common, prefering 1"
  [freqs]
  (->> freqs
       (#(sort-by (fn [v] (str (val v) (key v))) %))
       (apply max-key val)
       key))

(defn min-key-fn
  "Given frequencies of each digit returns least common, prefering 0"
  [freqs]
  (->> freqs
       (#(sort-by (fn [v] (str (val v) (key v))) %))
       reverse
       (apply min-key val)
       key))

(defn filtered-digits
  "Recursively filter out numbers based on most or least common digits"
  [key-fn numbers pos]
  (if (= 1 (count numbers))
    (first numbers)
    (recur key-fn
           (filter (fn [number]
                     (= (nth number pos)
                        (->> (map #(nth % pos) numbers)
                             frequencies
                             key-fn)))
                   numbers)
           (inc pos))))


(as-> input $
  (string/split-lines $)
  ((juxt #(filtered-digits max-key-fn % 0)
         #(filtered-digits min-key-fn % 0)) $)
  (map #(Integer/parseInt % 2) $)
  (apply * $))

