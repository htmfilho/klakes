(defproject klakes "0.1.0-SNAPSHOT"
  :description "Klakes (Knowledge Lakes) is a method of organizing knowledge in a consistent way."
  :url "klakes.github.io"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
            
  :dependencies [[org.clojure/clojure       "1.9.0" ]
                 
                 ; Web dependencies
                 [javax.servlet/servlet-api "2.5"   ]
                 [ring/ring-core            "1.6.3" ]
                 [ring/ring-devel           "1.6.3" ]
                 [http-kit                  "2.3.0" ]                 
                 [compojure                 "1.6.1" ]
                 [selmer                    "1.12.6"]
                 [org.clojure/data.json     "0.2.6" ]

                 ; Database dependencies
                 [org.clojure/java.jdbc     "0.7.0" ]
                 [org.xerial/sqlite-jdbc    "3.23.1"]
                 [com.layerware/hugsql      "0.4.9" ]
                 [ragtime                   "0.8.0" ]]
  
  :min-lein-version "2.0.0"
  :main ^:skip-aot klakes.app
  
  :profiles {:uberjar {:aot :all
                       :uberjar-name "klakes.jar"}})