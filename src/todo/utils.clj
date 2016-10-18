(ns todo.utils)

(defn parse-int [^String text]
    (Integer/parseInt text))

(defn get-width-of-text
    ^{:doc "Return count of letters in text"}
    ([^String text] (.length text)))

(defn digits-of-number
    ^{:doc "Return how many digits number have"}
    ([number] (todo.utils/get-width-of-text (str number))))