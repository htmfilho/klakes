(ns klakes.server
  (:require [ring.middleware.reload :refer [wrap-reload]]
            [clojure.java.browse    :refer [browse-url]]
            [taoensso.timbre        :as log]
            [org.httpkit.server     :refer [run-server]]))

(defonce server (atom nil))

(defn stop []
  (log/info "Shutting down ... Thanks for using Klakes!")
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start [router port]
  (reset! server (run-server (wrap-reload router) 
                             {:port port}))
  (log/info "Klakes is available at http://localhost:8080. To stop it, type Ctrl+C.")
  (browse-url (str "http://localhost:" port)))

(.addShutdownHook (Runtime/getRuntime) (Thread. stop))