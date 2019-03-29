(ns klakes.model.concept
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "klakes/model/sql/concept.sql")

(defn concepts-exist?
  "Return true if there is at least one concept in the database."
  []
  (not (<= 0 (concepts-exist))))