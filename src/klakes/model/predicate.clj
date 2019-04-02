(ns klakes.model.predicate)

(defn save [predicate]
  (println predicate)
  predicate)

(defn import-predicates [model]
  (let [predicates (model :predicates)]
    (map #(save %) predicates)
    model))
