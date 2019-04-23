(ns klakes.mdl.content
  (:require [clojure.java.jdbc :as jdbc]
            [hugsql.core       :as hugsql]
            [klakes.cache      :as cache]))

(hugsql/def-sqlvec-fns "klakes/mdl/sql/content.sql")

(defn find-by-id [id]
  (jdbc/get-by-id cache/db-spec :content id))

(defn find-by-reference [reference]
  (first (cache/run-query (find-by-reference-sqlvec {:reference reference}))))

(defn find-by-concept [concept]
  (cache/run-query (find-by-concept-sqlvec {:concept (:id concept)})))

(defn find-labels-frequence []
  (cache/run-query (find-labels-frequence-sqlvec)))

(defn insert-content [content]
  (let [content (dissoc content :id)
        id      (first (map val 
                            (first (jdbc/insert! cache/db-spec 
                                                 :content 
                                                 content))))]
    (assoc content :id id)))

(defn update-content [content]
  (let [content (dissoc content :id)]
    (jdbc/update! cache/db-spec 
                  :content 
                  content 
                  ["reference = ?" (:reference content)]))
  content)

(defn save [content]
  (when (content "content")
    (let [content (zipmap [:reference :title :url :excerpt :modified]
                          [((content "content") "id")
                           (content "title")
                           (content "url")
                           (content "excerpt")
                           (content "lastModified")])
          existing-content (find-by-reference (:reference content))]
      (if (empty? existing-content)
        (insert-content content)
        (update-content (assoc content :id (:id existing-content)))))))

(defn delete-associations-to-concept [concept]
  (jdbc/delete! cache/db-spec :concept_content ["concept = ?" (:id concept)]))

(defn associate-to-concept [concept contents]
  (delete-associations-to-concept concept)
  (map #(jdbc/insert! cache/db-spec 
                      :concept_content 
                      {:concept (:id concept) :content (:id %)}) contents))

(defn import-content [concept contents]
  (let [contents (filter #(some? %) (map save contents))]
    (associate-to-concept concept contents)))