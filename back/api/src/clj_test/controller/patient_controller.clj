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

(defn get-list
  [offset limit]
  (response {
      :data (map patient/to-view-identified (patient-service/get-list offset limit))
      :meta {:total (patient-service/get-total)}}))

(defn get-patient [id]
  (log/info (str "get-patient with id : " id))
  (let [results (patient-service/get-by-id id)]
    (cond (empty? results) {:status 404}
      :else (ok (patient/to-view-identified (first results))))))

(defn create [patient]
  (log/info (str "create-patient : " patient))
  (let [patient-created (patient-service/create patient)]
    (response (patient/to-view-identified patient-created))))

(defn upgrade [id patient]
  (log/info "id - " id)
  (log/info "controller update - " (str patient))
  (let [patient-updated (response (patient/to-view-identified (patient-service/upgrade id patient)))] patient-updated))

(defn delete [id]
  (log/info (str "delete patient with id : " id))
  (let [deleted-id (patient-service/delete id)]
    (log/info (str "id of patient deleted : " deleted-id))
    (if (= deleted-id 0) {:status 404} {:status 204})))

