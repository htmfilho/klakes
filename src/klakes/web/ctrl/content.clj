(ns klakes.web.ctrl.content
  (:require [selmer.parser      :as parser]
            [klakes.mdl.content :as mdl-content]))

(defn create-ranges [frequences font-sizes]
  (let [minimum       (apply min frequences)
        maximum       (apply max frequences)
        num-sizes     (count font-sizes)]
    (partition 2 1 
               (sort (conj (range minimum 
                                  maximum 
                                  (int (/ maximum num-sizes)))
                           maximum)))))

(defn size-ranges [frequences]
  (let [font-sizes (take (inc (count frequences)) (range 10 72 2))]
    (map #(assoc {} :range %1 :font-size %2)
       (create-ranges frequences font-sizes)
       font-sizes)))

(defn font-sizes [label-frequences]
  (let [frequences    (map #(:num %) label-frequences)
        ranges        (size-ranges frequences)]
    (loop [frequences frequences
           font-sizes []]
      (if (empty? frequences)
        font-sizes
        (recur (rest frequences)
               (conj font-sizes 
                     (:font-size (first (filter #(when (and (>= (first frequences) (first (:range %))) 
                                                          (<= (first frequences) (last (:range %)))) 
                                                     (:font-size %)) 
                                                ranges)))))))))

(defn append-font-sizes [label-frequences]
  (let [fnt-sizes (font-sizes label-frequences)]
    (map #(assoc %1 :font-size %2) label-frequences fnt-sizes)))

(defn tags [request]
  (parser/render-file "tags.html" {:tags (append-font-sizes (mdl-content/find-labels-frequence))}))