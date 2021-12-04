(require '[clojure.java.io :as io]
         '[clojure.string :as str])

(defn read-input [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

(def input (read-input "src/inputs/day2"))

(defn parse-direction [position direction]
  (let [[axis value] (str/split direction #" ")
        magnitude (Integer/parseInt value)]
    (case axis
      "forward" (update position :x + magnitude)
      "up" (update position :y - magnitude)
      "down" (update position :y + magnitude))))

(def part1
  (->> input
       (reduce parse-direction {:x 0 :y 0})
       vals
       (apply *)))

(defn parse-direction2 [position direction]
  (let [[axis value] (str/split direction #" ")
        magnitude (Integer/parseInt value)]
    (case axis
      "forward" (-> position
                    (update :x + magnitude)
                    (update :y + (* (:aim position) magnitude)))
      "up" (update position :aim - magnitude)
      "down" (update position :aim + magnitude))))

(def part2
  (->> input
       (reduce parse-direction2 {:x 0 :y 0 :aim 0})
       vals
       (take 2)
       (apply *)))

