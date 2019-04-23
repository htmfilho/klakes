(ns klakes.wiki
  (:require [clojure.data.json  :as json]
            [org.httpkit.client :as http]
            [config.core        :refer [env]]))

(defn get-content [concept callback]
  (let [options {:timeout 120000
                 :basic-auth [(env :wiki-user) (env :wiki-password)]
                 :query-params {:cql (str "label=" (:label concept))}
                 :headers {"Accept" "application/json"}}]
    (http/get (str (env :wiki-url) "/rest/api/search") 
              options
              (fn [{:keys [status headers body error]}]
                (if error
                  (println "Failed, exception is " error)
                  (println (callback concept ((json/read-str body) "results"))))))))