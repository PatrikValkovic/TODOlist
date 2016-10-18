(ns todo.menus.welcome-end
    (:require [todo.labels]
              [todo.utils]
              [todo.menus.print]))

(defn- generate-line-after-heading []
    (todo.menus.print/generate-line (todo.utils/get-width-of-text todo.labels/start-message)))

(defn- generate-line-before-end []
    (todo.menus.print/generate-line (todo.utils/get-width-of-text todo.labels/end-message)))

(defn print-start-message []
    (do (println todo.labels/start-message)
        (println (generate-line-after-heading))))

(defn print-end-message []
    (do
        (println (generate-line-before-end))
        (println todo.labels/end-message)))
