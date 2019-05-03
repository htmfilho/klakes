(ns klakes.app
  (:require [compojure.handler   :as handler]
            [klakes.config       :as config]
            [klakes.server       :as server]
            [klakes.web.routing  :as routing]
            [klakes.cache        :as cache])
  (:gen-class))

(defn -main [& [args]]
  (cache/migrate)
  (cache/define-busy-timeout 30000)
  (config/get-config)
  (let [router (handler/site #'routing/router)]
    (server/start router 8000)))
