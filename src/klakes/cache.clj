(ns klakes.cache
  (:require [clojure.java.jdbc :refer :all]
            [ragtime.jdbc      :as migration]
            [ragtime.repl      :as repl]))

(def config
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "cache.kb"})

(def migration-config
  {:datastore  (migration/sql-database {:connection-uri "jdbc:sqlite:cache.kb"})
   :migrations (migration/load-resources "migrations")})

(defn migrate []
  (repl/migrate migration-config))

(defmacro with-conn [& body]
  `(jdbc/with-db-connection [~'conn {:datasource datasource}] ~@body))