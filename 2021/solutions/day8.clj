(ns day8
  (:require [clojure.string :as string]))


(def sample-input
  "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")

(def input (slurp "input/day8.txt"))

;; A map from numbers to their textual (sorted) segment representations
(def segments
  {0 "abcefg"
   1 "cf"
   2 "acdeg"
   3 "acdfg"
   4 "bcdf"
   5 "abdfg"
   6 "abdefg"
   7 "acf"
   8 "abcdefg"
   9 "abcefg"})

(def unique-segments
  (->> (select-keys segments [1 4 7 8])
       vals
       (sort-by count)))

(def unique-lengths
  (->> ;; Work out the count of segments for numbers 1 4 7 8 to use as filter
   unique-segments
   (map count)
   set))


;; Part 1

(as-> input $
  (string/split-lines $)
  (map #(string/split % #" \| ") $)
  (map last $)
  (map #(string/split % #" ") $)
  (map #(map count %) $)
  (map #(filter unique-lengths %)
       $)
  (flatten $)
  (count $)
  (println "Part 1:" $))

;; Part 2

;; For each signal we first need to create a translation table
;; with scrambled letters to unscrambled letters. This can be achieved
;; by looking at the unique length numbers (1, 4, 7, 8) which should cover
;; all the segments and thus letters.


(defn signal->mapping
  [signal]
  (as-> (string/split signal #" ") $
    (filter #(unique-lengths (count %)) $)
    (sort-by count $)
    (map vector $ unique-segments)
    (map (fn [[signal segment]]
           (->> (map vector signal segment))) $)
    (flatten $)
    (partition 2 $)
    (map (partial apply vector) $)
    (into {} $)))

(comment
  (signal->mapping "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab")
  
  (map count (string/split "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab" #" "))
  )

(as-> input $
  (string/split-lines $)
  (map #(string/split % #" \| ") $)
  (map (fn [signal]
         (let [s (string/split #" " signal)])) $)
  #_(map #(string/split % #" ") $))


