(ns klakes.cache
  (:require [clojure.java.jdbc :as jdbc]
            [ragtime.jdbc      :as migration]
            [ragtime.repl      :as repl]))

(def db-spec
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "cache.db"})

(def conn-uri (str "jdbc:" (db-spec :subprotocol) ":" (db-spec :subname)))

(def migration-config
  {:migrations (migration/load-resources "migrations")
   :datastore  (migration/sql-database {:connection-uri conn-uri})})

(defn migrate []
  (repl/migrate migration-config))

(defn run-query [query]
  (jdbc/query db-spec query))

(defn define-busy-timeout [timeout]
  (run-query (str "pragma busy_timeout=" timeout)))