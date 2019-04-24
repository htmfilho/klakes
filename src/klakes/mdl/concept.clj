(ns klakes.mdl.concept
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/mdl/sql/concept.sql")

(defn concepts-exist? []
  (let [num-concepts (:c (first (cache/run-query (concepts-exist-sqlvec))))]
    (< 0 num-concepts)))

(defn find-by-id [id]
  (jdbc/get-by-id cache/db-spec :concept id))

(defn find-by-label [label]
  (first (cache/run-query (find-by-label-sqlvec {:label label}))))

(defn insert-concept [concept]
  (let [concept (dissoc concept :id)
        id      (first (map val (first (jdbc/insert! cache/db-spec :concept concept))))]
    (assoc concept :id id)))

(defn update-concept [concept]
  (let [concept (dissoc concept :id)]
    (jdbc/update! cache/db-spec :concept concept ["label = ?" (:label concept)])
    concept))

(defn save [concept]
  (if (empty? (find-by-label (:label concept)))
    (insert-concept concept)
    (update-concept concept)))

(defn import-concepts [model]
  (assoc model :concepts (map save (model :concepts))))