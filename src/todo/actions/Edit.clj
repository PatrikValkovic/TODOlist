(ns todo.actions.Edit
    (:require [todo.labels :as l]
              [todo.menus.print :as p]
              [clj-time.format :as f]
              [todo.utils :as u]))

(defn- format-todos [todos]
    (map (fn [entry]
             (:label entry))
         todos))

(defn- ask-for [question default fn]
    (do
        (println question (str \[ default \]))
        (let [entry (read-line)]
            (if (= 0 (u/get-width-of-text entry))
                default
                (fn entry)))))

(defn- ask-for-label [actual-label]
    (ask-for l/ask-for-label actual-label str))

(defn- ask-for-done [actual-done]
    (ask-for l/ask-for-done actual-done u/parse-bool))

(defn- ask-for-priority [actual-priority]
    (ask-for l/ask-for-priority actual-priority u/parse-int))

(defn- ask-for-when [actual-when]
    (f/parse
        u/input-formater
        (ask-for
            l/ask-for-when
            (f/unparse u/input-formater actual-when)
            (fn [str] str))))

(defn- edit-todo [instance]
    {:label    (ask-for-label (:label instance))
     :done     (ask-for-done (:done instance))
     :when     (ask-for-when (:when instance))
     :priority (ask-for-priority (:priority instance))})

(defn- edit-todos [todos index]
    (loop [actual       nil
           rest-todos   todos
           actual-index 1]
        (if (= 0 (count rest-todos))
            actual
            (if (= actual-index index)
                (recur (conj actual (edit-todo (first rest-todos))) (rest rest-todos) (inc actual-index))
                (recur (conj actual (first rest-todos)) (rest rest-todos) (inc actual-index))))
        ))

(defn edit [todo-ref]
    (do
        (println l/what-to-edit)
        (p/print-per-line (p/prepend-numbers (format-todos @todo-ref)))
        (dosync
            (alter todo-ref edit-todos (u/parse-int (read-line))))
        ))
