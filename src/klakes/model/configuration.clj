(ns klakes.model.configuration
  (:require [hugsql.core  :as hugsql]
            [klakes.cache :as cache]))

(hugsql/def-sqlvec-fns "klakes/model/sql/configuration.sql")

(defonce config (atom {:confluence-url ""
                       :confluence-user ""
                       :confluence-password ""
                       :confluence-timeout 120000}))

(defn save [parameter]
  (update-parameter cache/db-spec {:key (key parameter) 
                                   :value (val parameter)}))

(defn save-all [parameters]
  (map save parameters))