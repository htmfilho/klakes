(ns klakes.web.ctrl.knowledge-base
  (:require [clojure.java.io           :as io]
            [clojure.data.json         :as json]
            [ring.util.response        :refer [redirect]]
            [klakes.web.vis            :as vis]
            [klakes.mdl.triple         :as mdl-triple]
            [klakes.mdl.knowledge-base :as mdl-knowledge-base]))

(defn deserialize-knowledge-model
  "Reads the content of the file and transform it in data structures"
  [file]
  (json/read-str (slurp (:tempfile file)) :key-fn keyword))

(defn lakes-model [request]
  (let [concepts (mdl-triple/find-lakes)
        predicates (mdl-triple/find-by-subjects concepts)]
    (json/write-str {:concepts   (vis/concepts-to-nodes concepts)
                     :predicates (vis/triples-to-edges predicates)})))

(defn load-knowledge-model
  "Gets the file from the browser and save it for further use"
  [params]
  (let [model (deserialize-knowledge-model (params :model-file))]
    (println (mdl-knowledge-base/import-model model))
    (redirect "/")))