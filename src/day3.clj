(require '[clojure.java.io :as io]
         '[clojure.string :as str])

(defn read-input [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

(def input (read-input "src/inputs/day3"))

(defn ->binary [n] (Integer/toBinaryString n))
(defn ->dec [n] (Integer/parseInt n 2))

(defn find-common-bits [bits]
  (->> bits
       (map #(apply vector %))
       (apply mapv vector)
       (map frequencies)
       (map #(if (> (get % \0 0) (get % \1 0)) 0 1))
       (apply str)))

(defn negate-bits [bits mask-size]
  (let [negated (-> (apply str (repeat mask-size "1"))
                    ->dec
                    (bit-and-not bits)
                    ->binary)]
    (if (not= (count negated) mask-size)
      (str (apply str (repeat (- mask-size (count negated)) "0")) negated)
      negated)))

(def part-1
  (let [gamma (->dec (find-common-bits input))
        epsilon (->dec (negate-bits gamma (count (first input))))]
    (* gamma epsilon)))

(defn filter-max-bits
  ([bits]
   (filter-max-bits bits (find-common-bits bits) 0))
  ([bits bit-filter n]
   (let [filtered-bits (filter #(= (nth bit-filter n) (nth % n)) bits)
         new-filter (find-common-bits filtered-bits)
         new-n (inc n)]
     (if (= 1 (count filtered-bits))
       (first filtered-bits)
       (recur filtered-bits new-filter new-n)))))

(defn filter-min-bits
  ([bits]
   (filter-min-bits bits (negate-bits (->dec (find-common-bits bits)) (count (first bits))) 0))
  ([bits bit-filter n]
   (let [filtered-bits (filter #(= (nth bit-filter n) (nth % n)) bits)
         new-filter (negate-bits (->dec (find-common-bits filtered-bits)) (count (first bits)))
         new-n (inc n)]
     (if (= 1 (count filtered-bits))
       (first filtered-bits)
       (recur filtered-bits new-filter new-n)))))

(def part-2
  (let [oxygen-rating (->dec (filter-max-bits input))
        co2-rating (->dec (filter-min-bits input))]
    (* oxygen-rating co2-rating)))
