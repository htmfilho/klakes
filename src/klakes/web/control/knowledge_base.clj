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
  (map #(zipmap [:id :shape :label] [(:id %) "box" (:name %)]) concepts))

(defn serialize-knowledge-model [request]
  (let [concepts (mdl-triple/find-lakes)]
    (json/write-str (vis-nodes concepts))))

(defn load-knowledge-model
  "Gets the file from the browser and save it for further use"
  [params]
  (let [model (deserialize-knowledge-model (params :model-file))]
    (println (mdl-knowledge-base/import-model model))
    (redirect "/")))