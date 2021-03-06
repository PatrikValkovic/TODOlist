(ns todo.menus.print
    (:require [todo.labels]
              [todo.utils]))

;;generate line of specific width
(defn generate-line [width]
    (loop [actual (str "")
           rest   width]
        (if (= rest 0)
            actual
            (recur (.concat actual "-") (dec rest)))))

;;add number before each item and return it as string
(defn prepend-numbers [items]
    (map str (range 1 (+ 1 (count items)))
         (repeat (count items) " ") items))

(defn print-per-line [seq]
    (doseq [one seq]
        (println one)))

