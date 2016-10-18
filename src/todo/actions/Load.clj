(ns todo.actions.Load
    (:require [todo.labels]))

(defn load [todo-ref]
    (dosync
        (ref-set todo-ref (read-string (slurp todo.labels/store-file)))
        ))