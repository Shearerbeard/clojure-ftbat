(ns clojure-ftbat.chapter-03.exercises)

;; #2
(defn add-100 [x]
  (+ 100 x))

;; #3
(defn dec-maker [x]
  #(- % x))

;; #4
(defn map-set [f vec]
  (reduce
    #(conj %1 (f %2)) #{} vec))

