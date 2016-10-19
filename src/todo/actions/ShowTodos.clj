(ns todo.actions.ShowTodos
    (:require [todo.utils :as u]
              [todo.menus.print :as p]
              [clj-time.format :as f]
              [todo.sorting :as s]))

;;filter and format text
(defn- format-and-filter [todo-list]
    (map (fn [todo]
             (str
                 (if (true? (:done todo))
                     "\033[32m"                             ;hotovo
                     "\033[31m"                             ;udelat
                     )
                 (f/unparse u/output-formater (:when todo)) "  "
                 (:label todo)))
         (s/sort-todos todo-list)))

;;print all entries
(defn print-entries [entries]
    (do
        (println todo.labels/your-todos)
        (p/print-per-line entries)
        (print "\033[0m")))

;;generate line after last todos entry
(defn print-line-after-todos
    ([todolist plus] (println (p/generate-line
                                  (+ plus
                                     (do
                                         (u/get-width-of-text
                                             (last todolist)))))))
    ([todolist]
        (print-line-after-todos todolist 0))
    )

(defn print-todos [todolist]
    (if (= 0 (count todolist))
        (println "No entry")
        (do
            (print-entries (format-and-filter todolist))
            (print-line-after-todos (format-and-filter todolist) -5)
            (u/wait-for-enter))
        ))
