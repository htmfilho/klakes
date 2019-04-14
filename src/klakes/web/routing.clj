(ns klakes.web.routing
  (:require [compojure.core                 :as url]
            [compojure.route                :as route]
            [selmer.parser                  :as parser]
            [clojure.java.io                :as io]
            [klakes.server                  :as server]
            [klakes.web.ctrl.home           :as ctrl-home]
            [klakes.web.ctrl.concept        :as ctrl-concept]
            [klakes.web.ctrl.knowledge-base :as ctrl-knowledge-base]))

(parser/set-resource-path!  (clojure.java.io/resource "html"))

(defn quit [request]
  (server/stop)
  (str "Bye!"))

(defn routes []
  (url/routes
    (url/GET  "/"                request 
                                 (ctrl-home/home request))
    (url/GET  "/concept/:id"     {{id :id} :params} 
                                 (ctrl-concept/concept-view id))
    (url/GET  "/quit"            request 
                                 (quit request))
    (url/GET  "/api/model/lakes" request
                                 (ctrl-knowledge-base/serialize-knowledge-model request))
    (url/POST "/model/load"      {params :params}
                                 (ctrl-knowledge-base/load-knowledge-model params))))

(url/defroutes router
  (routes)
  (route/resources "/")
  (url/ANY "*" [] (route/not-found (slurp (io/resource "public/404.html")))))