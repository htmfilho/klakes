(ns klakes.model.knowledge-base
  (:require [klakes.model.concept :as mdl-concept]))

(defn model-exists?
  "Returns true if all conditions that indicates that a model exists also return true."
  [] 
  (mdl-concept/concepts-exist?))

(defn import-knowledge-model [] nil)
