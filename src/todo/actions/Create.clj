(ns todo.actions.Create
    (:require [todo.labels]))

(defn- ask-for-name []
    (do
        (println todo.labels/write-name)
        (read-line)))

(defn- create-new-entry []
    {:label (ask-for-name)
     :done false})

(defn create [todo-ref]
    (dosync
        (alter todo-ref conj (create-new-entry))))
