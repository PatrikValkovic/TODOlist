(ns todo.menus.menu
    (:require [todo.labels]
              [todo.menus.print]))

;takes only odds
(defn- take-odds [inputs]                                   ;is equal to loop version
    (if (< 0 (count inputs))
        (conj (take-odds (rest (rest inputs))) (first inputs))
        (list)))
(defn- take-odds-loop [inputs]
    (loop [actual (list)
           index  (dec (dec (count inputs)))]
        (if (>= index 0)
            (recur (conj actual (nth inputs index)) (dec (dec index)))
            actual
            )))

(defn- print-menu [to-print]
    {:pre (even? (count to-print))}
    (todo.menus.print/print-per-line (todo.menus.print/prepend-numbers (take-odds-loop to-print))))

;;validate if is number between count and 1
(defn- validate-input [number count]
    (and
        (>= number 1)
        (<= number count))
    )

;;load number from input
(defn- get-number []
    (. Integer parseInt (read-line)))

;;ask user for input
(defn- ask-user [to-print]
    (do
        (print-menu to-print)
        (println todo.labels/your-choice)
        (get-number)))

;;ask user for action
(defn- get-number-of-method [to-print]
    (loop [actual (ask-user to-print)]
        (if (validate-input actual (/ (count to-print) 2))
            (dec (* actual 2))
            (do
                (println todo.labels/wrong-entry)
                (recur (ask-user to-print))))
        ))

;;print menu and ask user for action
(defn solve-menu [& more]
    {:pre [(even? (count more))]}
    ((nth more (get-number-of-method more)))
    )





