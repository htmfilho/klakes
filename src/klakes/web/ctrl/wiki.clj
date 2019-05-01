(ns klakes.web.ctrl.wiki
  (:require [ring.util.response      :as res]
            [klakes.web.ctrl.concept :as ctrl-concept]))

(defn auth [params session]
  (-> (res/response (ctrl-concept/concept-view (:id params) session))
      (assoc :session session)))