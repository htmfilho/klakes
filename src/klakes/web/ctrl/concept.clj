(ns klakes.web.ctrl.concept
  (:require [clojure.data.json  :as json]
            [org.httpkit.client :as http]
            [selmer.parser      :as parser]
            [config.core        :refer [env]]
            [klakes.web.vis     :as vis]
            [klakes.mdl.concept :as mdl-concept]
            [klakes.mdl.triple  :as mdl-triple]
            [klakes.mdl.content :as mdl-content]))

(defn load-content [concept content]
  (let [content ((json/read-str content) "results")]
    (mdl-content/import-content concept content)))

(defn get-content [concept options]
  (http/get (str (env :wiki-url) "/rest/api/search") options
    (fn [{:keys [status headers body error]}]
      (if error
        (println "Failed, exception is " error)
        (println (load-content concept body))))))

(defn concept-view [id]
  (let [concept (mdl-concept/find-by-id id)
        options {:timeout 120000
                 :basic-auth [(env :wiki-user) (env :wiki-password)]
                 :query-params {:cql (str "label=" (:label concept))}
                 :headers {"Accept" "application/json"}}]
    (get-content concept options)
    (parser/render-file "concept.html" {:concept concept
                                        :model-exists (mdl-triple/model-exists concept)
                                        :contents (mdl-content/find-by-concept concept)
                                        :portal-url (env :wiki-url)})))

(defn concept-model [id]
  (let [concepts (mdl-triple/find-by-parent id)
        predicates (mdl-triple/find-by-subjects concepts)]
    (json/write-str {:concepts   (vis/concepts-to-nodes concepts)
                     :predicates (vis/triples-to-edges predicates)})))