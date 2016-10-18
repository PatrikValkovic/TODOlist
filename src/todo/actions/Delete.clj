(ns todo.actions.Delete
    (:require [todo.labels :as l]
              [todo.menus.print :as p]
              [todo.utils :as u]))

(defn- format-entries [todos]
    (map (fn [entry]
             (:label entry))
         todos))

(defn- delete-entry [todos index]
    (loop [another todos
           actual-index 1
           actual-seq nil]

        (if (= 0 (count another))
            actual-seq
            (if (= actual-index index)
                (recur (rest another) (inc actual-index) actual-seq)
                (recur (rest another) (inc actual-index) (conj actual-seq (first another)))))

        ))

(defn delete [todo-ref]
    (do
        (p/print-per-line (p/prepend-numbers (format-entries @todo-ref)))
        (println l/what-to-delete)
        (dosync
            (alter todo-ref delete-entry (u/parse-int (read-line))))
        ))
