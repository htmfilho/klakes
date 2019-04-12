(ns klakes.model.triple
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/model/sql/triple.sql")

(defn find-lakes 
  "Returns all subjects and objects that don't have parents in the triples"
  []
  (cache/run-query (find-lakes-sqlvec)))

(defn delete-all []
  (jdbc/execute! cache/db-spec (delete-all-sqlvec)))

(defn save [triple]
  (first (jdbc/insert! cache/db-spec :triple triple))
  triple)

(defn import-triples [model]
  (delete-all)
  (assoc model :triples (map save (model :triples))))