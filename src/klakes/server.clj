(ns klakes.server
  (:require [clojure.java.browse :refer [browse-url]]
            [org.httpkit.server  :only  [run-server]]))

(defonce server (atom nil))

(defn start [handler port]
  (reset! server (run-server handler {:port port}))
  (println "Klakes is available at http://localhost:8080. To stop it, type Ctrl+C.")
  (browse-url (str "http://localhost:" port)))

(defn stop []
  (println "Shutting down ... Thanks for using Klakes!")
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))