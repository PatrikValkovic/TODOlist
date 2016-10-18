(ns todo.actions.Load
    (:require [todo.labels]))

(defn load-todos [todo-ref]
    (dosync
        (ref-set todo-ref (read-string (slurp todo.labels/store-file)))
        ))