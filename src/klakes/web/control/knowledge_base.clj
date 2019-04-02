(ns klakes.web.control.knowledge-base
  (:require [clojure.java.io             :as io]
            [clojure.data.json           :as json]
            [klakes.model.knowledge-base :as mdl-knowledge-base]))

(defn deserialize-knowledge-model
  "Reads the content of the file and transform it in data structures"
  [file]
  (json/read-str (slurp (:tempfile file)) :key-fn keyword))

(defn load-knowledge-model
  "Gets the file from the browser and save it for further use"
  [params]
  (let [model (deserialize-knowledge-model (params :model-file))]
    (str (mdl-knowledge-base/import-model model))))