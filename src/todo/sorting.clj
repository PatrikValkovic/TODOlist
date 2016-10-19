(ns todo.sorting
    (:require [clj-time.core :as t]))

(defn- filter-done [todos]
    (filter (fn [entry] (:done entry)) todos))

(defn- filter-undone [todos]
    (filter (fn [entry] (not (:done entry))) todos))

(defn- filter-expired [todos]
    (filter (fn [entry] (t/before? (:when entry) (t/now))) todos))

(defn- filter-nonexpired [todos]
    (filter (fn [entry] (t/after? (:when entry) (t/now))) todos))

(defn- sort-by-date-and-priority [todos]
    (sort-by
        (fn [entry] (* (:priority entry) (t/in-days (t/interval (t/now) (:when entry)))))
        todos))

(defn- sort-by-date [todos]
    (sort
        (fn [first second]
            (if (t/before? (:when first) (:when second))
                -1
                (if (t/equal? (:when first) (:when second))
                    0
                    1)))
        todos))

;first show expired entries sorted by date
;then show undone entries sorted by date and priority
;last show done entries sorted by date

(defn sort-todos [todo-list]
    (concat (sort-by-date (filter-expired (filter-undone todo-list)))
            (sort-by-date-and-priority (filter-nonexpired (filter-undone todo-list)))
            (sort-by-date (filter-done todo-list)))
    )