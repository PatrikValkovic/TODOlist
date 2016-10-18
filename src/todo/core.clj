(ns todo.core
    (:require [todo.menus.menu])
    (:require [todo.menus.welcome-end])
    (:require [todo.menus.print])
    (:require [todo.utils])
    (:require [todo.actions.MarkAsDone])
    (:require [todo.actions.ShowOnMainPage])
    (:require [todo.actions.ShowTodos])
    (:require [todo.actions.Create]))

(todo.menus.welcome-end/print-start-message)

(def todo-list (ref (list {:label "One entry of todo list"
                           :done false}
                          {:label "Next entry of todo list"
                           :done false}
                          {:label "What else"
                           :done true})))

(loop [continue true]
    (if (not (false? continue))
        (do
            (todo.actions.ShowOnMainPage/print-todo-list @todo-list)
            (recur (todo.menus.menu/solve-menu
                       "Show all todos" #(todo.actions.ShowTodos/print-todos @todo-list)
                       "Mark entry as done" #(todo.actions.MarkAsDone/mark-entry-as-done todo-list)
                       "Create new entry" #(todo.actions.Create/create todo-list)
                       "Edit entry" #(println "Edit entry")
                       "Delete entry" #(println "Delete entry")
                       "Store list" #(println "Store list")
                       "Load list" #(println "Load list")
                       "End app" #(do false))))
        continue))

(todo.menus.welcome-end/print-end-message)

