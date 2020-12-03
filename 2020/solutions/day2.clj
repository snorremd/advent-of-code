(ns day2
  (:require [clojure.string :as string]
            [clojure.edn :refer [read-string]]))

(defn transform
  "Reads numbers as numbers, and converts string to char for letter"
  [col]
  (-> col
      (update 0 read-string)
      (update 1 read-string)
      (update 2 first)))

(defn char-frequency
  "Finds frequencies of letter in password"
  [pass-meta]
  (assoc pass-meta
         :appears
         (get (frequencies (:pass pass-meta))
              (:letter pass-meta)
              0)))

(defn valid-pass-1?
  "Password checker for task 1"
  [{:keys [min appears max]}]
  (<= min appears max))

(defn valid-pass-2?
  "Password checker for task 2"
  [{:keys [pass min max letter]}]
  (->> [(get pass (dec min))
        (get pass (dec max))]
       (filter #(= letter %))
       count
       (= 1)))

;; Read input and divide into sequence of numbers
(def passwords (->> "./input/day2.txt"
                    slurp
                    string/split-lines
                    (map #(->> %
                               (re-matcher #"(\d*)-(\d*)\s([a-z]):\s([a-z]*)")
                               re-find
                               (drop 1)
                               vec
                               transform))
                    (map (partial zipmap [:min :max :letter :pass]))
                    (map char-frequency)))

;; First task
(count (filter valid-pass-1? passwords))

;; Second task
(count (filter valid-pass-2? passwords))