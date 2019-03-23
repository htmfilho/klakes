(ns klakes.server
  (:use [org.httpkit.server  :only  [run-server]])
  (:require [clojure.java.browse :refer [browse-url]]))

(defonce server (atom nil))

(defn start [handler port]
  (browse-url (str "http://localhost:" port))
  (reset! server (run-server handler {:port port})))

(defn stop []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))