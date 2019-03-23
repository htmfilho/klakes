(ns klakes.app
  (:use     [org.httpkit.server  :only  [run-server]])
  (:require [clojure.java.browse :refer [browse-url]]
            [compojure.handler   :as handler]
            [klakes.web.routing  :as routing])
  (:gen-class))



(defn -main [& [args]]
  (println "Klakes is available at http://localhost:8080. To stop it, type Ctrl+C.")
  (let [router (handler/site #'routing/router)]
    (browse-url "http://localhost:3000")
    (run-server router {:port 3000})))
