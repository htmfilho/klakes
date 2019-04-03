(ns klakes.model.concept
  (:require [hugsql.core  :as hugsql]
            [klakes.cache :as cache]))

(hugsql/def-sqlvec-fns "klakes/model/sql/concept.sql")

(defn concepts-exist?
  "Return true if there is at least one concept in the database."
  []
  (let [num-concepts (:c (first (cache/run-query (concepts-exist-sqlvec))))]
    (not (<= 0 num-concepts))))

(defn find-by-label [label]
  (cache/run-query (find-by-label-sqlvec {:label label})))

(defn save [concept]
  concept)

(defn import-concepts [model]
  (let [concepts (model :concepts)]
    (map #(save %) concepts)
    model))