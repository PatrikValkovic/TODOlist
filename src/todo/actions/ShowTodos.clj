(ns todo.actions.ShowTodos
    (:require [todo.utils :as u]
              [todo.menus.print]
              [clj-time.format :as f]))

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
         todo-list))

;;print all entries
(defn print-entries [entries]
    (do
        (println todo.labels/your-todos)
        (todo.menus.print/print-per-line entries)
        (print "\033[0m")))

;;generate line after last todos entry
(defn print-line-after-todos
    ([todolist plus] (println (todo.menus.print/generate-line
                                  (+ plus
                                     (do
                                         (todo.utils/get-width-of-text
                                             (last todolist)))))))
    ([todolist]
        (print-line-after-todos todolist 0))
    )

(defn print-todos [todolist]
    (if (= 0 (count todolist))
        (println "No entry")
        (do
            (todo.actions.ShowTodos/print-entries (format-and-filter todolist))
            (todo.actions.ShowTodos/print-line-after-todos (format-and-filter todolist) -5)
            (todo.utils/wait-for-enter))
        ))
