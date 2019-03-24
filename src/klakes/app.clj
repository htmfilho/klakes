(ns klakes.app
  (:require [compojure.handler   :as handler]
            [klakes.server       :as server]
            [klakes.web.routing  :as routing]
            [klakes.cache        :as cache])
  (:gen-class))

(defn -main [& [args]]
  (println "Klakes is available at http://localhost:8080. To stop it, type Ctrl+C.")
  (cache/migrate)
  (let [router (handler/site #'routing/router)]
    (server/start router 3000)))
