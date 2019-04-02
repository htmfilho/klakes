(ns klakes.model.triple)

(defn save [triple]
  (println triple)
  triple)

(defn import-triples [model]
  (let [triples (model :triples)]
    (map #(save %) triples)
    model))