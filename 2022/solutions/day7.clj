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

(defn read-file-description
    [line]
    (let [[size filename] (string/split line #" ")]
        [filename (read-string size)]))

(defn go-to-child
  "Returns the zipper at the child node's location."
  [zipper child]
  (->> zipper
       z/down
       (iterate z/right)
       (drop-while #(or (not (z/branch? %))
                        (not= (-> % z/node first) child)))
       first))

(defn run-cmd [zp line]
  (cond (= line "$ cd ..")
        (z/up zp)

        (string/starts-with? line "$ cd ")
        (go-to-child zp (string/replace line "$ cd " ""))

        (string/starts-with? line "dir ")
        (z/append-child zp [(string/replace line "dir " "") 0])

        (re-matches #"^\d+\s.*" line)
        (z/append-child zp (read-file-description line))

        :else zp))


(as-> sample-input $
  (string/split-lines $)
  (reduce run-cmd ;; Builds directory structure as vector tree
          (z/vector-zip ["/" 0])
          (drop 1 $))
  (z/root $) ;; return to root of tree
  (dir-size $)
  #_(drop-while z/branch? $)
  #_(reduce (fn [acc line] ;; Here we calculate and collect directory sizes
            (z/next))
          {:zp $ :size 0}
          $))


(comment
  fs
  (as-> zp zp
    (z/append-child zp ["a" []])
    (z/append-child zp ["b.txt" 14848514])
    (z/append-child zp ["c.dat" 8504156])
    (z/append-child zp ["d" []])
    (go-to-child zp "d")
    (z/node zp))

  #_(z/append-child zp ["e"])
  #_(z/append-child zp ["f" 29116])
  #_(z/append-child zp ["g" 2557])
  #_(z/append-child zp ["h.lst" 62596])
  #_(z/up zp)
  #_(z/root zp)
  )
  