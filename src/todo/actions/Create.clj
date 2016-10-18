(ns todo.actions.Create
    (:require [todo.labels :as l]
              [clj-time.format :as f]
              [todo.utils :as u]))

(defn- ask-for-name []
    (do
        (println l/write-name)
        (read-line)))

(defn- ask-for-when []
    (do
        (println l/write-when)
        (f/parse u/input-formater (read-line))
        ))

(defn- ask-for-priority []
    (do
        (println l/write-priority)
        (let [in (read-line)]
            (if (= 0 (u/get-width-of-text in))
                10
                (u/parse-int in)))
        ))

(defn- create-new-entry []
    {:label (ask-for-name)
     :done false
     :when (ask-for-when)
     :priority (ask-for-priority)})

(defn create [todo-ref]
    (dosync
        (alter todo-ref conj (create-new-entry))))
