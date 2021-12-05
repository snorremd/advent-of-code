(ns day4
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(def input (slurp "input/day4.txt"))

(def sample-input
  "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")

(def puzzle-input input)

(def parse-int #(Integer/parseInt %))

(defn input->draws
  [puzzle-input]
  (as-> puzzle-input $
      (string/split $ #"\n\n")
      (first $)
      (string/split $ #",")
      (map parse-int $)))

(defn board-str->board
  "Given a single board string with new-lines and whitespaces
   returns map of {:rows [] :cols []} each vector containing the
   string values for the board"
  [board-str]
  (as-> board-str $
    (string/split-lines $)
    (map (comp #(string/split % #"\s+")
               string/trim) $)
    (map (partial #(map parse-int %)) $)
    (assoc {}
           :rows $
           :cols (apply map vector $))))

(defn input->boards
  "Sequence of boards encoded as a map of {:rows [] :cols []}"
  [puzzle-input]
  (as->  puzzle-input $
    (string/split $ #"\n\n")
    (rest $)
    (map board-str->board $)
    (map (fn [board] (-> board 
                         (update :rows #(map set %))
                         (update :cols #(map set %))))
         $)))


(defn match?
  "Checks if board rows or cols match draws"
  [draws {:keys [rows cols]}]
  (or (some #(empty? (set/difference % draws)) rows)
      (some #(empty? (set/difference % draws)) cols)))


;; Part 1
;; Loop recur until a match is found and calculate the score

(loop [boards (input->boards puzzle-input)
       draws (drop 5 (input->draws puzzle-input))
       curr-draws (take 5 (input->draws puzzle-input))]
  (let [matching (filter (partial match? (set curr-draws)) boards)]
    (if-not (empty? matching)
      (* (first curr-draws)
         (apply + (set/difference (->> matching first :rows (apply concat) set)
                                  (set curr-draws))))
      (recur boards
             (rest draws)
             (conj curr-draws (first draws))))))


;; Part 2
(loop [boards (input->boards puzzle-input)
       draws (drop 5 (input->draws puzzle-input))
       curr-draws (vec (take 5 (input->draws puzzle-input)))
       winning []]
  (if (or (empty? draws) (empty? boards)) ;; Exhausted the possible draws or boards
    (* (-> winning last :curr-draws last)
       (apply + (set/difference (->> winning last :board :rows (apply concat) set)
                                (->> winning last :curr-draws set))))
    (let [winning-boards (filter (partial match? (set curr-draws)) boards)]
      (recur (filter (fn [board] (not (some #(= % board) winning-boards))) boards)
             (rest draws)
             (conj curr-draws (first draws))
             (concat winning (map (fn [board] {:curr-draws curr-draws
                                               :board board})
                                  winning-boards))))))


;; Rich comments to test out functionality
(comment
  (conj [1] 4)

  (concat [10 20] [])

  (board-str->board
   "22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19")
  
  ;; No match
  (match? #{12 24 32 14 25} {:rows [#{12 24 32 14 21}] :cols [#{12 24 32 14 21}]})
  
  ;; Row match
  (match? #{12 24 32 14 25} {:rows [#{12 24 32 14 25}] :cols [#{12 24 32 14 21}]})

  ;; Col match
  (match? #{12 24 32 14 25} {:rows [#{12 24 32 14 21}] :cols [#{12 24 32 14 25}]})
  
  (input->draws puzzle-input)
  (input->boards puzzle-input)

  (set/difference #{"22" "13" "17" "11" "0"} #{"22" "14" "13" "17" "11" "0"})

  (set/union #{"22" "13" "17" "11" "0"} (set ["32" "42" "22"]))

  (concat #{24 22} #{23 12})

  (conj #{"22"} "23")

  (take 1 )

  )
