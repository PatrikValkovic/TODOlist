(ns todo.actions.Delete
    (:require [todo.labels]
              [todo.menus.print]
              [todo.utils]))

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
        (todo.menus.print/print-per-line (todo.menus.print/prepend-numbers (format-entries @todo-ref)))
        (println todo.labels/what-to-delete)
        (dosync
            (alter todo-ref delete-entry (todo.utils/parse-int (read-line))))
        ))
