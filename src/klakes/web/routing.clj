(ns klakes.web.routing
  (:require [compojure.core   :as url]
            [compojure.route  :as route]
            [selmer.parser    :as parser]
            [clojure.java.io  :as io]))

(parser/set-resource-path!  (clojure.java.io/resource "html"))

(defn handler [request]
  (parser/render-file "home.html" {}))

(defn routes []
  (url/routes
    (url/GET "/" request (handler request))))

(url/defroutes router
  (routes)
  (route/resources "/")
  (url/ANY "*" [] (route/not-found (slurp (io/resource "public/404.html")))))