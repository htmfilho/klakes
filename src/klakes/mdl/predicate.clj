(ns klakes.mdl.predicate
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/mdl/sql/predicate.sql")

(defn find-by-verb [verb]
  (first (cache/run-query (find-by-verb-sqlvec {:verb verb}))))

(defn insert-predicate [predicate]
  (let [predicate (dissoc predicate :id)
        id        (first (map val (first (jdbc/insert! cache/db-spec :predicate predicate))))]
    (assoc predicate :id id)))

(defn update-predicate [predicate]
    (let [predicate (dissoc predicate :id)]
      (jdbc/update! cache/db-spec :predicate predicate ["verb = ?" (:verb predicate)])
      predicate))

(defn save [predicate]
  (if (empty? (find-by-verb (:verb predicate)))
    (insert-predicate predicate)
    (update-predicate predicate)))

(defn import-predicates [model]
  (assoc model :predicates (map save (model :predicates))))