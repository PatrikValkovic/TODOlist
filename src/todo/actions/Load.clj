(ns todo.actions.Load
    (:require [todo.labels :as l]))

(defn load-todos [todo-ref]
    (dosync
        (ref-set todo-ref (read-string (slurp l/store-file)))
        ))