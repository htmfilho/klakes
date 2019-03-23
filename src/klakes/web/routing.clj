(ns klakes.web.routing
  (:require [compojure.core                  :as url]
            [compojure.route                 :as route]
            [clojure.java.io                 :as io]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Klakes"})

(defn routes []
  (url/routes
    (url/GET "/" request (handler request))))

(url/defroutes router
  (routes)
  (route/resources "/")
  (url/ANY "*" [] (route/not-found (slurp (io/resource "public/404.html")))))