(ns todo.utils
    (:require [todo.labels]))

(defn parse-bool [^String text]
    (if (= text "true")
        true
        (if (= text "True")
            true
            (if (= text "Yes")
                true
                (if (= text "yes")
                    true
                    (if (= text "y")
                        true
                        (if (= text "Y")
                            true
                            false)))))))

(defn parse-int [^String text]
    (Integer/parseInt text))

(defn get-width-of-text
    ^{:doc "Return count of letters in text"}
    ([^String text] (.length text)))

(defn digits-of-number
    ^{:doc "Return how many digits number have"}
    ([number] (todo.utils/get-width-of-text (str number))))

(defn wait-for-enter
    ([] (do
            (println todo.labels/press-enter-to-continue)
            (read-line))))