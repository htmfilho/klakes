(ns klakes.web.control.home
  (:require [selmer.parser :as parser]
            [klakes.model.knowledge-base :as mdl-knowledge-base]))

(defn home [request]
  (parser/render-file "home.html" {:model-exists (mdl-knowledge-base/model-exists?)}))