(ns klakes.mdl.knowledge-base
  (:require [klakes.mdl.concept   :as mdl-concept]
            [klakes.mdl.predicate :as mdl-predicate]
            [klakes.mdl.triple    :as mdl-triple]))

(defn model-exists? []
  (mdl-concept/concepts-exist?))

(defn import-model [model]
  (-> model
    (mdl-concept/import-concepts)
    (mdl-predicate/import-predicates)
    (mdl-triple/import-triples)))
