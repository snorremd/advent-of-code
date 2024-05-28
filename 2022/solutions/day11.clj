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

(def input (slurp "input/day11.txt"))

(defn parse-monkey
  [monkey]
  {:id (->> monkey
            (re-find #"Monkey (\d+):")
            last
            read-string)
   :inspected 0
   :items (->> monkey
               string/split-lines
               second
               (re-seq #"(\d+)")
               (mapv (comp read-string last)))
   :operation (->> (re-find #"Operation: new \= (old|\d+) ([\+\-\*]) (old|\d+)" monkey)
                   rest
                   (apply format "(fn [old] (%2$s %1$s %3$s))")
                   read-string
                   eval)
   :relief (->> (re-find #"divisible by (\d+)" monkey)
                last
                read-string
                (partial #(mod %2 %1))
                (comp zero?))
   :test-fn (->> (re-find #"divisible by (\d+)" monkey)
                 last
                 read-string
                 (partial #(mod %2 %1))
                 (comp zero?))
   :test (let [test-fn (->> (re-find #"divisible by (\d+)" monkey)
                            last
                            read-string
                            (partial #(mod %2 %1))
                            (comp zero?))
               monkeys (->> (re-seq #"throw to monkey (\d+)" monkey)
                            (map (comp read-string last)))]
           (fn [n]
             (if (test-fn n)
               (first monkeys)
               (second monkeys))))})

(defn part-1
  [monkeys turn]
  (let [{:keys [items operation test]} (monkeys turn)]
    (-> (reduce (fn [acc item]
                  (let [item' (-> item operation (/ 3) int)]
                    (update-in acc [(test item') :items] conj item')))
                monkeys items)
        (update-in [turn :inspected] (partial + (count items)))
        (assoc-in [turn :items] []))))

(defn part-2
  [monkeys turn]
  (let [{:keys [items operation test test-fn test-n]} (monkeys turn)]
    (-> (reduce (fn [acc item]
                  (let [item' (operation item)
                        result (test-fn item)]
                    (update-in acc [(test item') :items] conj (if result (mod ) item'))))
                monkeys items)
        (update-in [turn :inspected] (partial + (count items)))
        (assoc-in [turn :items] []))))


(as-> input $
  (string/split $ #"\n\n")
  (mapv parse-monkey $)
  (iterate #(reduce part-1 % (range (count %))) $)
  (take (inc 20) $)
  (last $)
  (map :inspected $)
  (reverse (sort $))
  (take 2 $)
  (apply * $)
  (println "Part 1:" $))


(as-> sample-input $
  (string/split $ #"\n\n")
  (mapv parse-monkey $)
  (iterate #(reduce part-2 % (range (count %))) $)
  (take (inc 20) $)
  (last $)
  #_(map :inspected $)
  #_(reverse (sort $))
  #_(take 2 $)
  #_(apply * $)
  #_(println "Part 2:" $))