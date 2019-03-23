(ns klakes.app
  (:use     [org.httpkit.server  :only  [run-server]])
  (:require [clojure.java.browse :refer [browse-url]])
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Klakes"})

(defn -main [& [args]]
  (browse-url "http://localhost:8080")
  (println "To stop Klakes, type Ctrl+C.")
  (run-server handler {:port 8080}))
