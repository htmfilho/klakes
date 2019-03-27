(ns klakes.cache
  (:require [clojure.java.jdbc :refer :all]
            [ragtime.jdbc      :as migration]
            [ragtime.repl      :as repl]))

(def config
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "cache.db"})

(def migration-config
  {:migrations (migration/load-resources "migrations")
   :datastore  (migration/sql-database {:connection-uri (str "jdbc:" 
                                                             (config :subprotocol) 
                                                             ":" 
                                                             (config :subname))})})

(defn migrate []
  (repl/migrate migration-config))

(defmacro with-conn [& body]
  `(jdbc/with-db-connection [~'conn {:datasource datasource}] ~@body))