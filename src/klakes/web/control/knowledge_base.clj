(ns klakes.web.control.knowledge-base
  (:require [ring.util.response          :refer [redirect]]
            [clojure.java.io             :as io]
            [clojure.data.json           :as json]
            [klakes.model.triple         :as mdl-triple]
            [klakes.model.knowledge-base :as mdl-knowledge-base]))

(defn deserialize-knowledge-model
  "Reads the content of the file and transform it in data structures"
  [file]
  (json/read-str (slurp (:tempfile file)) :key-fn keyword))

(defn vis-nodes [concepts]
  (map #(zipmap [:id :shape :label] 
                [(:id %) "box" (:name %)]) 
                concepts))

(defn vis-edges [triples]
  (map #(zipmap [:from :to :arrows :font :label] 
                [(:subject %) (:object %) "to" {:align "top"} (:name %)]) 
                triples))

(defn serialize-knowledge-model [request]
  (let [concepts (mdl-triple/find-lakes)
        predicates (mdl-triple/find-by-subjects concepts)]
    (json/write-str {:concepts   (vis-nodes concepts)
                     :predicates (vis-edges predicates)})))

(defn load-knowledge-model
  "Gets the file from the browser and save it for further use"
  [params]
  (let [model (deserialize-knowledge-model (params :model-file))]
    (println (mdl-knowledge-base/import-model model))
    (redirect "/")))