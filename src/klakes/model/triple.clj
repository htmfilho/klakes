(ns klakes.model.triple
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/model/sql/triple.sql"))

(defn delete-all 
  "Remove all triples from the database"
  []
  (execute! cache/db-cache (delete-all-sqlvec)))

(defn save [triple]
  (first (jdbc/insert! cache/db-spec :triple triple))
  triple)

(defn import-triples [model]
  (delete-all)
  (map save (model :triples)))