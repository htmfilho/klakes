(ns klakes.web.ctrl.wiki
  (:require [ring.util.response      :as res]
            [klakes.web.ctrl.concept :as ctrl-concept]))

(defn auth [params session]
  (let [session (assoc session :user (:user params) 
                               :password (:password params))]
    (-> (res/response (ctrl-concept/concept-view (:id params) session))
        (assoc :session session))))