(ns klakes.web.ctrl.concept
  (:require [clojure.data.json  :as json]
            [selmer.parser      :as parser]
            [config.core        :refer [env]]
            [klakes.web.vis     :as vis]
            [klakes.wiki        :as wiki]
            [klakes.mdl.concept :as mdl-concept]
            [klakes.mdl.triple  :as mdl-triple]
            [klakes.mdl.content :as mdl-content]))

(defn concept-view [id]
  (let [concept (mdl-concept/find-by-id id)]
    (wiki/get-content concept mdl-content/import-content)
    (parser/render-file "concept.html" {:concept concept
                                        :model-exists (mdl-triple/model-exists concept)
                                        :contents (mdl-content/find-by-concept concept)
                                        :influenced (mdl-triple/find-subjects-by-object concept)
                                        :influences (mdl-triple/find-objects-by-subject concept)
                                        :portal-url (env :wiki-url)})))

(defn concept-model [id]
  (let [concepts (mdl-triple/find-by-parent id)
        predicates (mdl-triple/find-by-subjects concepts)]
    (json/write-str {:concepts   (vis/concepts-to-nodes concepts)
                     :predicates (vis/triples-to-edges predicates)})))