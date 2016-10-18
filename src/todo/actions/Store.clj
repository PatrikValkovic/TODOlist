(ns todo.actions.Store
    (:require [todo.labels]))

(defn store [todo-list]
    (spit todo.labels/store-file (prn-str todo-list)))