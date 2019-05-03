(ns klakes.mdl.wiki
  (:require [clojure.data.json  :as json]
            [taoensso.timbre    :as log]
            [org.httpkit.client :as http]
            [klakes.config      :as config]))

(defn get-content [concept callback credentials]
  (if (or (empty? (:wiki-url (config/get-config)))
          (nil? (:user credentials))
          (nil? (:password credentials)))
    nil
    (let [options {:timeout 120000
                   :basic-auth [(:user credentials) (:password credentials)]
                   :query-params {:cql (str "label=" (:label concept))}
                   :headers {"Accept" "application/json"}}]
      (http/get (str (:wiki-url (config/get-config)) "/rest/api/search")
                options
                (fn [{:keys [status headers body error]}]
                  (if error
                    (log/error error)
                    (log/info (callback concept ((json/read-str body) "results")))))))))