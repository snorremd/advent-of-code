(ns day5
  (:require [clojure.string :as string]))

(defn parse-code
  "A non-efficient recursive function to do a binary lookup of numbers.
   Could have been implemented by converting characters to 0s and 1s and
   parsing the string as a binary number. This shows of more of Clojure by
   using recur."
  [code low high]
  (let [last? (= 1 (count code))
        midpoint (+ low (int (/ (- high low) 2)))]
    (case (first code)
      (\F \L) (if last?
                low
                (recur (rest code) low midpoint))
      (\B \R) (if last?
                high
                (recur (rest code) (inc midpoint) high)))))

(def boarding-passes
  "Read in the bording pass data and split into rows and cols"
  (->> "./input/day5.txt"
       slurp
       string/split-lines
       (map #(string/split % #"(?=[R|L])" 2))))


(def codes
  "Turn boarding pass data into codes"
  (->> boarding-passes
       (map (fn [[row col]]
              [(parse-code row 0 127)
               (parse-code col 0 7)]))
       (map #(+ (* (first %) 8) (second %)))))


;; Task 1
(apply max codes)

;; Task 2
(->> codes
     sort
     (partition 2 1)
     (filter #(= (apply - (reverse %)) 2))
     ffirst
     inc)


(comment
  ;; Some simple test examples for the binary code parser
  (= (parse-code "LLL" 0 7) 0)
  (= (parse-code "RRR" 0 7) 7)
  (= (parse-code "LRR" 0 7) 3)
  (= (parse-code "FFFFFF" 0 127) 0)
  (= (parse-code "BBBBBB" 0 127) 127))