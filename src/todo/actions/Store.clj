(ns todo.actions.Store
    (:require [todo.labels :as l]))

(defn store [todo-list]
    (spit l/store-file (prn-str todo-list)))