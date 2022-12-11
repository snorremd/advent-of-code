(ns day11
  (:require [clojure.string :as string]))

(def sample-input
  "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")


(defn parse-monkey
  [monkey]
  {:items (->> monkey
               (re-find #"items: (\d+), (\d+)*")
               rest
               (map read-string))
   :operation (->> (re-find #"Operation: new \= (old|\d+) ([\+\-\*]) (old|\d+)" monkey)
                   rest
                   (apply format "(fn [old] (%2$s %1$s %3$s))")
                   read-string
                   eval)
   :test (let [test-fn (->> (re-find #"Test: divisible by (\d+)" monkey)
                            last
                            read-string
                            (partial mod)
                            (comp zero?))
               monkeys (->> (re-seq #"If true \: throw to monkey (\d+)" monkey)
                            (map (comp read-string last)))]
           (fn [n] (if (test-fn n)
                     (first monkeys)
                     (second monkeys))))})

(as-> sample-input $
  (string/split $ #"\n\n")
  (map parse-monkey $))