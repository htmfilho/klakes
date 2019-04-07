(ns klakes.model.triple)

(defn save [triple]
  triple)

(defn import-triples [model]
  (map save (model :triples)))