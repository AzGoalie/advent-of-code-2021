(require '[clojure.java.io :as io])

(defn read-input [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

(def input (read-input "src/inputs/day1"))

(def part-1
  (->> input
       (map #(Integer/parseInt %))
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))

(def part-2
  (->> input
       (map #(Integer/parseInt %))
       (partition 3 1)
       (map #(apply + %))
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))
