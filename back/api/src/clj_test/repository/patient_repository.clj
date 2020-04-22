(ns clj-test.repository.patient-repository
  (:use [korma.core :only [select insert values where set-fields exec-raw offset limit aggregate group fields]])
  (:require [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient-model]))

(defn get-list
  "Gets list patients from the database"
  [ofs lim]
  (select patient-model/patient
    (limit lim)
    (offset ofs)))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (log/info (str "get-by-id repository, id: " id))
  (select patient-model/patient (where {:id id})))

(defn get-total
  "Get total patients database"
  []
  (get (first (exec-raw ["SELECT COUNT(*) FROM patients"] :results)) :count))

(defn create
  "Adds a new patient to the database"
  [patient]
  (log/info (str "create-patient repository : " patient))
  (first (exec-raw ["INSERT INTO patients (full_name, gender, dob, address, oms_number) VALUES (?, ?::gender, to_date(?,'YYYY-MM-DD'), ?, ?) RETURNING *"
              [(get patient :full_name)
               (get patient :gender)
               (str (get patient :dob))
               (get patient :address)
               (get patient :oms_number)
              ]] :results)))

(defn upgrade
  "Updates an existing patient on the database"
  [id patient]
  (first (exec-raw ["UPDATE patients SET full_name = ?, gender = ?::gender, dob = to_date(?, 'YYYY-MM-DD'), address = ?, oms_number = ? WHERE id = ? RETURNING *"
              [(get patient :full_name)
               (get patient :gender)
               (str (get patient :dob))
               (get patient :address)
               (get patient :oms_number)
               id
              ]] :results)))

(defn delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [id]
  (log/info (str "repo delete, id : " id))
  (korma.core/delete patient-model/patient (where {:id id})))

