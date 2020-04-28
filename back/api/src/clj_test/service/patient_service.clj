(ns clj-test.service.patient-service
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]
            [clj-test.repository.patient-repository :as patient-repo]))

(defn get-list
  "Gets list patinets from the database"
  [offset limit]
  (patient-repo/get-list offset limit))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (patient-repo/get-by-id id))

(defn get-total
  "Gets total patients in database"
  []
  (patient-repo/get-total))

(defn create
  "Adds a new patient to the database, returns the id if ok, null if invalid"
  [patient]
  (patient-repo/create patient))

(defn upgrade
  "Updates an existing patient on the database"
  [id data]
  (patient-repo/upgrade id data))

(defn delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [id]
  (patient-repo/delete id))

