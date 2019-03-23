(ns klakes.web.routing
  (:require [compojure.core   :as url]
            [compojure.route  :as route]
            [selmer.parser    :as parser]
            [clojure.java.io  :as io]
            [klakes.server    :as server]))

(parser/set-resource-path!  (clojure.java.io/resource "html"))

(defn home [request]
  (parser/render-file "home.html" {}))

(defn content [request]
  (parser/render-file "content.html" {}))

(defn quit [request]
  (server/stop)
  (str "Bye!"))

(defn routes []
  (url/routes
    (url/GET "/"               request (home request))
    (url/GET "/content/:label" request (content request))
    (url/GET "/quit"           request (quit request))))

(url/defroutes router
  (routes)
  (route/resources "/")
  (url/ANY "*" [] (route/not-found (slurp (io/resource "public/404.html")))))