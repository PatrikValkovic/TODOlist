(ns todo.actions.MarkAsDone
    (:require [todo.actions.ShowTodos :as st]
              [todo.menus.print :as p]
              [todo.labels :as l]
              [todo.utils :as u]))

(defn- filter-and-format [todos]
    (map (fn [todo]
             (:label todo))
         (filter (fn [todo] (false? (:done todo)))
                 todos))
    )

(defn- function-to-change [todo-list indexToMark]
    (loop [seq          nil
           rest-of-todo todo-list
           countOf      0]

        (if (= 0 (count rest-of-todo))
            seq
            (if (true? (:done (first rest-of-todo)))
                (recur (conj seq (first rest-of-todo)) (rest rest-of-todo) countOf)
                (if (= indexToMark (inc countOf))
                    (recur (conj seq {:label (:label (first rest-of-todo)), :done true}) (rest rest-of-todo) (inc countOf))
                    (recur (conj seq (first rest-of-todo)) (rest rest-of-todo) (inc countOf)))))

        ))

(defn mark-entry-as-done [todos-as-ref]
    (do
        (st/print-entries (p/prepend-numbers (filter-and-format @todos-as-ref)))
        (println l/what-to-done)
        (dosync
            (alter todos-as-ref function-to-change (u/parse-int (read-line))))
        ))