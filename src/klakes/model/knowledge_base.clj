(ns klakes.model.knowledge-base
  (:require [klakes.model.concept   :as mdl-concept]
            [klakes.model.predicate :as mdl-predicate]
            [klakes.model.triple    :as mdl-triple]))

(defn model-exists?
  "Returns true if all conditions that indicates that a model exists also return true."
  [] 
  (mdl-concept/concepts-exist?))

(defn import-model [model]
  (mdl-concept/import-concepts model)
  (mdl-predicate/import-predicates model)
  (mdl-triple/import-triples model))
