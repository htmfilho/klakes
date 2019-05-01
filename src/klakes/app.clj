(ns klakes.app
  (:require [clojure.edn         :as edn]
            [clojure.java.io     :as io]
            [compojure.handler   :as handler]
            [klakes.server       :as server]
            [klakes.web.routing  :as routing]
            [klakes.cache        :as cache])
  (:gen-class))

(defn read-edn [file]
  (with-open [in (java.io.PushbackReader. (io/reader file))]
    (edn/read in)))

(defn load-config []
  (let [config-file-name "config.edn"]
    (if (.exists (io/file config-file-name))
      (println (read-edn config-file-name))
      (spit config-file-name "{:wiki-url \"\"}"))))

(defn -main [& [args]]
  (cache/migrate)
  (cache/define-busy-timeout 30000)
  (load-config)
  (let [router (handler/site #'routing/router)]
    (server/start router 8000)))
