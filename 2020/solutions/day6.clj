(ns day6
  (:require [clojure.string :as string]
            [clojure.set :as sets]))

(def groups
  "Get the input and separate groups"
  (-> "./input/day6.txt"
      slurp
      (string/split #"(?im)^\s*$")))

;; Task 1
(->> groups
     (map #(string/replace % "\n" ""))
     (map (comp count distinct))
     (apply +))

;; Task 2
(->> groups
     (map string/trim)
     (map string/split-lines)
     (map #(map set %))
     (map #(apply sets/intersection %))
     (map count)
     (apply +))

