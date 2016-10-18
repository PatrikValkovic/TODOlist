(ns todo.core
    (:require [todo.menus.menu]
              [todo.menus.welcome-end]
              [todo.actions.MarkAsDone]
              [todo.actions.ShowOnMainPage]
              [todo.actions.ShowTodos]
              [todo.actions.Create]
              [todo.actions.Delete]
              [todo.actions.Load]
              [todo.actions.Store]
              [todo.actions.Edit]))

(todo.menus.welcome-end/print-start-message)

(def todo-list (ref (list {:label "One entry of todo list"
                           :done  false}
                          {:label "Next entry of todo list"
                           :done  false}
                          {:label "What else"
                           :done  true})))

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


