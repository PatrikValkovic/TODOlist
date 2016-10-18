(ns todo.actions.ShowTodos
    (:require [todo.utils]
              [todo.menus.print]))

;;filter and format text
(defn- format-and-filter [todo-list]
    (map (fn [todo]
             (if (true? (:done todo))
                 (str "\033[32m" (:label todo))             ;hotovo
                 (str "\033[31m" (:label todo))             ;udelat
                 ))
         todo-list))

;;print all entries
(defn print-entries [entries]
    (do
        (println todo.labels/your-todos)
        (todo.menus.print/print-per-line entries)
        (print "\033[0m")))

;;generate line after last todos entry
(defn print-line-after-todos [todolist]
    (println (todo.menus.print/generate-line
                 (+ (inc (todo.utils/digits-of-number (count todolist)))
                    (do
                        (todo.utils/get-width-of-text
                            (last todolist)))))))

(defn print-todos [todolist]
    (if (= 0 (count todolist))
        (println "No entry")
        (do
            (todo.actions.ShowTodos/print-entries (format-and-filter todolist))
            (todo.actions.ShowTodos/print-line-after-todos (format-and-filter todolist)))
        ))
