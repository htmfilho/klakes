(ns klakes.web.routing
  (:require [compojure.core                 :as url]
            [compojure.route                :as route]
            [selmer.parser                  :as parser]
            [clojure.java.io                :as io]
            [klakes.server                  :as server]
            [klakes.web.ctrl.home           :as ctrl-home]
            [klakes.web.ctrl.concept        :as ctrl-concept]
            [klakes.web.ctrl.content        :as ctrl-content]
            [klakes.web.ctrl.knowledge-base :as ctrl-knowledge-base]
            [klakes.web.ctrl.wiki           :as ctrl-wiki]))

(parser/set-resource-path!  (clojure.java.io/resource "html"))

(defn quit [request]
  (server/stop))

(defn routes []
  (url/routes
    (url/GET  "/"
              request 
              (ctrl-home/home request))
    (url/GET  "/lakes"
              request 
              (ctrl-home/home request))
    (url/GET  "/concepts/:id{[0-9]+}"
              {{id :id} :params session :session}
              (ctrl-concept/concept-view id session))
    (url/GET  "/tags"
              request 
              (ctrl-content/tags request))
    (url/GET  "/quit"
              request 
              (quit request))
    (url/GET  "/api/model/lakes"
              request
              (ctrl-knowledge-base/lakes-model request))
    (url/GET  "/api/model/concepts/:id{[0-9]+}"
              {{id :id} :params}
              (ctrl-concept/concept-model id))
    
    (url/POST "/model/load"
              {params :params}
              (ctrl-knowledge-base/load-knowledge-model params))
    (url/POST "/wiki/auth"
              {params :params session :session}
              (ctrl-wiki/auth params session))))

(url/defroutes router
  (routes)
  (route/resources "/")
  (url/ANY "*" [] (route/not-found (slurp (io/resource "public/404.html")))))