(ns klakes.mdl.concept
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/mdl/sql/concept.sql")

(defn concepts-exist?
  "Return true if there is at least one concept in the database."
  []
  (let [num-concepts (:c (first (cache/run-query (concepts-exist-sqlvec))))]
    (< 0 num-concepts)))

(defn find-by-label [label]
  (first (cache/run-query (find-by-label-sqlvec {:label label}))))

(defn insert-it
  "Returns a map of fields persisted in the database."
  [concept]
  (let [concept (dissoc concept :id)
        id      (first (map val (first (jdbc/insert! cache/db-spec :concept concept))))]
    (assoc concept :id id)))

(defn update-it
  "Returns the number of records updated in the database."
  [concept]
  (let [concept (dissoc concept :id)]
    (jdbc/update! cache/db-spec :concept concept ["label = ?" (:label concept)])
    concept))

(defn save 
  "If the object doesn't exist it returns the id of the recently persisted 
   object. If the object exists it returns the id that comes with the object 
   only if at least one object is updated or zero if no object is updated."
  [concept]
  (if (empty? (find-by-label (:label concept)))
    (insert-it concept)
    (update-it concept)))

(defn import-concepts [model]
  (assoc model :concepts (map save (model :concepts))))