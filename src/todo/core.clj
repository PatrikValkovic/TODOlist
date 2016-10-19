(ns todo.core
    (:require [clj-time.core :as t]
              [todo.menus.menu]
              [todo.menus.welcome-end]
              [todo.actions.MarkAsDone]
              [todo.actions.ShowOnMainPage]
              [todo.actions.ShowTodos]
              [todo.actions.Create]
              [todo.actions.Delete]
              [todo.actions.Load]
              [todo.actions.Store]
              [todo.actions.Edit]
              [todo.sorting :as s]))

(todo.menus.welcome-end/print-start-message)

(def todo-list (ref (list {:label "Entry with priority 10"
                           :done  false
                           :when (t/date-time 2016 11 1)
                           :priority 10}
                          {:label "Entry with priority 5"
                           :done  false
                           :when (t/date-time 2016 11 1)
                           :priority 5}
                          {:label "Done task"
                           :done  true
                           :when (t/date-time 2016 10 10)
                           :priority 10}
                          {:label "Expired task"
                           :done  false
                           :when (t/date-time 2016 10 10)
                           :priority 1})))

(println "SORTED" (s/sort-todos @todo-list))

(loop [continue true]
    (if (not (false? continue))
        (do
            (todo.actions.ShowOnMainPage/print-todo-list @todo-list)
            (recur (todo.menus.menu/solve-menu
                       "Show all todos" #(todo.actions.ShowTodos/print-todos @todo-list)
                       "Mark entry as done" #(todo.actions.MarkAsDone/mark-entry-as-done todo-list)
                       "Create new entry" #(todo.actions.Create/create todo-list)
                       "Edit entry" #(todo.actions.Edit/edit todo-list)
                       "Delete entry" #(todo.actions.Delete/delete todo-list)
                       "Store list" #(todo.actions.Store/store @todo-list)
                       "Load list" #(todo.actions.Load/load-todos todo-list)
                       "End app" #(do false))))
        continue))

(todo.menus.welcome-end/print-end-message)


