(ns todo.actions.Edit
    (:require [todo.utils]
              [todo.labels]
              [todo.menus.print]))

(defn- format-todos [todos]
    (map (fn [entry]
             (:label entry))
         todos))

(defn- ask-for-label [actual-label]
    (do
        (println todo.labels/ask-for-label (str \[ actual-label \]))
        (let [entry (read-line)]
            (if (= 0 (todo.utils/get-width-of-text entry))
                actual-label
                entry)
            )))

(defn- ask-for-done [actual-done]
    (do
        (println todo.labels/ask-for-done (str \[ actual-done \]))
        (let [entry (read-line)]
            (if (= 0 (todo.utils/get-width-of-text entry))
                actual-done
                (todo.utils/parse-bool entry)))))

(defn- edit-todo [instance]
    {:label (ask-for-label (:label instance))
     :done  (ask-for-done (:done instance))})

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
        (println todo.labels/what-to-edit)
        (todo.menus.print/print-per-line (todo.menus.print/prepend-numbers (format-todos @todo-ref)))
        (dosync
            (alter todo-ref edit-todos (todo.utils/parse-int (read-line))))
        ))
