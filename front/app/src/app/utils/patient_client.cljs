(ns app.utils.patient-client
    (:require
      [reagent.core :as r :refer [atom]]
      [ajax.core :refer [GET POST PUT DELETE]]
      [app.utils.logger :as logger]))

(def api-url "/api/patients")

(defn create-patient
  [patient handler]
  (POST api-url {:params patient
                 :format :json
                 :handler handler
                 :response-format :json}))

(defn update-patient
  [id patient handler]
  (PUT (str api-url "/" id) {:params patient
                 :format :json
                 :handler handler
                 :response-format :json}))

(defn get-patients
  [offset limit handler]
  (GET api-url {:params {:offset (or offset 0)
                                 :limit (or limit 10)}
                :handler handler 
                :error-handler (fn [r] (logger/log r))
                :response-format :json
                :keywords? true}))

(defn delete-patient
  [id handler]
  (DELETE (str api-url "/" id) {:handler handler
                                :response-format :json}))

