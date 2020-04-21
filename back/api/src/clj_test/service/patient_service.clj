(ns clj-test.service.patient-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]
            [clj-test.repository.patient-repository :as patient-repo]))

(defn get-all
  "Gets all patinets from the database"
  []
  (patient-repo/get-all))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (patient-repo/get-by-id id))

(defn create
  "Adds a new patient to the database, returns the id if ok, null if invalid"
  [patient]
  (let [patient-created (patient-repo/create patient)] patient-created))

(defn upgrade
  "Updates an existing patient on the database"
  [id data]
  (let [patient (patient-repo/upgrade id data)] patient))

(defn delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [id]
  (patient-repo/delete id))

