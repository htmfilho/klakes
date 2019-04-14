(ns klakes.mdl.predicate
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/mdl/sql/predicate.sql")

(defn find-by-verb [verb]
  (first (cache/run-query (find-by-verb-sqlvec {:verb verb}))))

(defn insert-predicate
  "Returns a map of fields persisted in the database."
  [predicate]
  (let [predicate (dissoc predicate :id)
        id        (first (map val (first (jdbc/insert! cache/db-spec :predicate predicate))))]
    (assoc predicate :id id)))

(defn update-predicate
  "Returns the number of records updated in the database."
  [predicate]
    (let [predicate (dissoc predicate :id)]
      (jdbc/update! cache/db-spec :predicate predicate ["verb = ?" (:verb predicate)])
      predicate))

(defn save 
  "If the object doesn't exist it returns the id of the recently persisted 
   object. If the object exists it returns the id that comes with the object 
   only if at least one object is updated or zero if no object is updated."
  [predicate]
  (if (empty? (find-by-verb (:verb predicate)))
    (insert-predicate predicate)
    (update-predicate predicate)))

(defn import-predicates [model]
  (assoc model :predicates (map save (model :predicates))))