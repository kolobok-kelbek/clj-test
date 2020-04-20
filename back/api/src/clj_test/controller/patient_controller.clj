(ns clj-test.controller.patient-controller
  (:use [ring.util.response :only [response]])
  (:require [compojure.core :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as route]
            [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]
            [ring.middleware.json :as ring-json]
            [clj-test.service.patient-service :as patient-service]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]))

(defn get-all []
  (response
      (patient-service/get-all)))

(defn get-patient [id]
  (log/info (str "get-patient with id : " id))
  (let [results (patient-service/get-by-id id)]
    (cond (empty? results) {:status 404}
      :else (ok (first results)))))

(defn create [patient]
  (log/info (str "create-patient : " patient))
  (let [patient-created (patient-service/create patient)]
    (response (patient/to-view-identified patient-created))))

