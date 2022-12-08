(ns day7
  (:require [clojure.string :as string]
            [clojure.zip :as z]))

(def sample-input
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def input (slurp "input/day7.txt"))

(defn backwalk [f loc]
  (if (nil? (z/prev loc))
    (f loc)
    (recur f (z/prev (f loc)))))

(defn read-file-description
    [line] 
    (let [[size filename] (string/split line #" ")]
        {:path filename :size (read-string size)}))

(defn go-to-child
  "Returns the zipper at the child node's location."
  [loc child]
  (->> loc
       z/down
       (iterate z/right)
       (drop-while #(not= (-> % z/node :path) child))
       first))

(defn run-cmd [loc line]
  (cond (= line "$ cd ..")
        (z/up loc)

        (string/starts-with? line "$ cd ")
        (go-to-child loc (string/replace line "$ cd " ""))

        (string/starts-with? line "dir ")
        (z/append-child loc {:path (string/replace line "dir " "") :size 0 :children []})

        (re-matches #"^\d+\s.*" line)
        (z/append-child loc (read-file-description line))
        
        :else loc))

(defn calculate-size
  [loc]
  (if (z/branch? loc)
    (z/edit loc
            #(assoc %1 :size %2)
            (->> loc z/node :children (map :size) (reduce +)))
    loc))

(defn build-directories
  [input]
  (as-> input $
    (string/split-lines $)
    (reduce run-cmd ;; Builds directory structure as vector tree
            (z/zipper #(contains? % :children) :children #(assoc %1 :children %2) {:path "/" :size 0 :children []})
            (drop 1 $))
    (backwalk calculate-size $)))

(as-> input $
  (build-directories $)
  (iterate z/next $)
  (take-while (comp not z/end?) $)
  (filter z/branch? $) 
  (map (comp :size z/node) $)
  (filter #(<= % 100000) $)
  (reduce + $)
  (println "Part 1:" $))

(as-> input $
  (build-directories $)
  (let [remaining (- 70000000 (-> $ z/node :size))
        missing (- 30000000 remaining)]
    (->> $ (iterate z/next)
         (take-while (comp not z/end?))
         (filter z/branch?)
         (filter #(>= (->> % z/node :size) missing))
         (sort-by (comp :size z/node)) 
         first
         z/node
         :size))
  (println "Part 2:" $))
  