(ns klakes.model.predicate)

(defn save [predicate]
  predicate)

(defn import-predicates [model]
  (map save (model :predicates)))