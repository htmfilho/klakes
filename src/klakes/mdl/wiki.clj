(ns klakes.mdl.wiki
  (:require [clojure.data.json  :as json]
            [taoensso.timbre    :as log]
            [org.httpkit.client :as http]
            [config.core        :refer [env]]))

(defn get-content [concept callback credentials]
  (if (nil? (:wiki-url env))
    nil
    (let [options {:timeout 120000
                   :basic-auth [(:user credentials) (:password credentials)]
                   :query-params {:cql (str "label=" (:label concept))}
                   :headers {"Accept" "application/json"}}]
      (http/get (str (env :wiki-url) "/rest/api/search")
                options
                (fn [{:keys [status headers body error]}]
                  (if error
                    (log/error error)
                    (log/info (callback concept ((json/read-str body) "results")))))))))