(ns klakes.web.ctrl.home
  (:require [selmer.parser             :as parser]
            [klakes.mdl.knowledge-base :as mdl-knowledge-base]))

(defn home [request]
  (parser/render-file "home.html" {:model-exists (mdl-knowledge-base/model-exists?)}))