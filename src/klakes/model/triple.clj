(ns klakes.model.triple
  (:require [clojure.java.jdbc      :as jdbc]
            [hugsql.core            :as hugsql]
            [klakes.cache           :as cache]
            [klakes.model.concept   :as mdl-concept]
            [klakes.model.predicate :as mdl-predicate]))

(hugsql/def-sqlvec-fns "klakes/model/sql/triple.sql")

(defn find-lakes 
  "Returns all subjects and objects that don't have parents in the triples"
  []
  (cache/run-query (find-lakes-sqlvec)))

(defn find-by-subjects [subjects]
  (cache/run-query (find-by-subjects-sqlvec {:subjects (map #(:id %) subjects)})))

(defn delete-all []
  (jdbc/execute! cache/db-spec (delete-all-sqlvec)))

(defn save [triple]
  (let [subject   (mdl-concept/find-by-label (triple :subject))
        predicate (mdl-predicate/find-by-verb (triple :predicate))
        object    (mdl-concept/find-by-label (triple :object))
        parent    (if (nil? (triple :parent)) 
                    {} 
                    (mdl-concept/find-by-label (triple :parent)))
        triple    {:subject (subject :id)
                   :predicate (predicate :id)
                   :object (object :id)
                   :parent (parent :id)}]
    (first (jdbc/insert! cache/db-spec :triple triple))
    triple))

(defn import-triples [model]
  (delete-all)
  (assoc model :triples (map save (model :triples))))