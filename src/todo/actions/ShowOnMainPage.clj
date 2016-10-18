(ns todo.actions.ShowOnMainPage
    (:require [todo.actions.ShowTodos]
              [clj-time.format :as df]
              [todo.utils :as u]))

;;filter what todos to show and than transform it to string format
(defn- format-and-filter [todos]
    (map (fn [todo]
             (str (:label todo)))
         (filter (fn [todo] (not (:done todo)))
                 todos))
    )

;;print todolist as preview on main page
(defn print-todo-list [todolist]
    (if (= 0 (count (format-and-filter todolist)))
        nil
        (do
            (todo.actions.ShowTodos/print-entries (todo.menus.print/prepend-numbers (format-and-filter todolist)))
            (todo.actions.ShowTodos/print-line-after-todos (format-and-filter todolist) (inc (todo.utils/digits-of-number (count (format-and-filter todolist))))))
        ))