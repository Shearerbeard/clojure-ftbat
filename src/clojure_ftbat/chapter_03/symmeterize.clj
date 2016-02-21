(ns clojure-ftbat.chapter-03.symmeterize)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn matching-part
  [part prefixes & [key]]
  (map (fn[pref]
         (let [ptrn (if key (re-pattern key) #"^left-")
               p {:name (clojure.string/replace
                          (:name part) ptrn pref)
                  :size (:size part)}]
           p))
       prefixes))

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts prefixes]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (conj (matching-part part prefixes) part))))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (symmetrize-body-parts asym-body-parts ["right-" "upper-right-" "top" "upper-left"])
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
            accumulated-size (:size part)]
       (if (> accumulated-size target)
         part
         (recur remaining (+ accumulated-size (:size (first remaining))))))))
