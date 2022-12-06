(ns day6)

(def input (slurp "input/day6.txt"))
(def sample-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def sample-input2 "bvwbjplbgvbhsrlpgdmjqwftvncz")

(defn solve [i length]
  (as-> i $
    (partition length 1 $)
    (map frequencies  $)
    (take-while #(not= 1 (apply max (vals %))) $)
    (+ length (count $))))

(->> (solve input 4)
     (println "Part 1:"))

(->> (solve input 14)
     (println "Part 2:"))
