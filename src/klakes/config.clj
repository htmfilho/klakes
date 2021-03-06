(ns klakes.config
  (:require [clojure.edn         :as edn]
            [clojure.java.io     :as io]))

(defn read-edn [file]
  (with-open [in (java.io.PushbackReader. (io/reader file))]
    (edn/read in)))

(defn get-config []
  (let [config-file-name "config.edn"]
    (when (not (.exists (io/file config-file-name)))
      (spit config-file-name "{:wiki-url \"\"}"))
    (read-edn config-file-name)))