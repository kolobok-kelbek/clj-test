(ns clj-test.repository.patient-repository
  (:use [korma.core :only [select insert values delete where set-fields exec-raw]])
  (:require [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient-model]))

(defn get-all
  "Gets all patients from the database"
  []
  (select patient-model/patient))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (log/info (str "get-by-id repository, id: " id))
  (select patient-model/patient (where {:id id})))

(defn create
  "Adds a new patient to the database"
  [patient]
  (log/info (str "create-patient repository : " patient))
  (first (exec-raw ["INSERT INTO patients (full_name, gender, dob, address, oms_number) VALUES (?, ?::gender, to_date(?,'YYYY-DD-MM'), ?, ?) RETURNING *"
              [(get patient :full_name)
               (get patient :gender)
               (.format (java.text.SimpleDateFormat. "yyyy-dd-MM") (get patient :dob))
               (get patient :address)
               (get patient :oms_number)
              ]] :results)))

