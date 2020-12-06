(ns day4
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.set :as sets]))

(def expected
  "Expected passport fields"
  #{:ecl :byr :iyr :hgt :pid :hcl :eyr})

(def transformers
  "Transformation functions for passport values to make them easier
   to validate"
  {:byr read-string
   :iyr read-string
   :eyr read-string
   :hgt #(as-> % arg
           (string/split arg #"(?=[a-z])" 2)
           (zipmap [:height :unit] arg)
           (update arg :height read-string))
   :hcl str
   :ecl str
   :pid str
   :cid str})

(defn parse-values
  "Parse each value into more useful data types"
  [passport]
  (reduce-kv (fn [acc k v]
               (let [key (keyword k)]
                 (assoc acc key ((key transformers) v))))
             {}
             passport))

(defn clean-passport
  "Process the passport string into a nice clojure map
   where each passport field is a key value pair"
  [s]
  (-> (string/trim s)
      (string/split #"\s")
      (->> (map #(string/split % #":")))
      (->> (into {}))
      parse-values))

(defn valid-passport?
  [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (and (<= 1920 byr 2002)
       (<= 2010 iyr 2020)
       (<= 2020 eyr 2030)
       (case (:unit hgt)
         "cm" (<= 150 (:height hgt) 193)
         "in" (<= 59 (:height hgt) 76)
         false)
       (re-matches #"#[a-f0-9]{6}" hcl)
       (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)
       (re-matches #"^[0-9]{9}$" pid)))

(def passports
  "Get the input and turn it into clojure maps with each field
   as a key value pair."
  (-> "./input/day4.txt"
      slurp
      (string/split #"(?im)^\s*$")
      (->> (map clean-passport))))

;; Solution 1, simply list the passport field keys and use superset to
;; check against the expected field.
(->> passports
     (filter #(sets/superset? (set (keys %)) expected))
     count)

;; Solution 2, 
(->> passports
     (filter #(sets/superset? (set (keys %)) expected))
     (filter valid-passport?)
     count)


(->> passports
     (map #(get-in % [:hgt :unit])))