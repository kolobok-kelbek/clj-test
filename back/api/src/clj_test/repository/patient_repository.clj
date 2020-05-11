(ns clj-test.repository.patient-repository
  (:require [clj-test.config.db :as db-config]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient-model]))

(defn- db-query
  [query]
  (jdbc/query db-config/db-spec query))

(defn- db-exec
  [query]
  (jdbc/execute! db-config/db-spec query))

(defn get-list
  "Gets list patients from the database"
  [offset limit]
  (db-query ["SELECT * FROM patients ORDER BY id ASC LIMIT ? OFFSET ?" limit offset]))

(defn get-by-id
  "Gets only one patient using its id"
  [id]
  (log/info (str "get-by-id repository, id: " id))
  (first (db-query ["SELECT * FROM patients WHERE id = ?" id])))


(defn get-total
  "Get total patients database"
  []
  (get (first (db-query ["SELECT COUNT(*) FROM patients"])) :count))

(defn create
  "Adds a new patient to the database"
  [patient]
  (log/info (str "create-patient repository : " patient))
  (first (db-query ["INSERT INTO patients (full_name, gender, dob, address, oms_number) VALUES (?, ?::gender, to_date(?,'YYYY-MM-DD'), ?, ?) RETURNING *"
              (get patient :full_name)
              (get patient :gender)
              (str (get patient :dob))
              (get patient :address)
              (get patient :oms_number)
              ])))

(defn upgrade
  "Updates an existing patient on the database"
  [id patient]
  (first (db-query ["UPDATE patients SET full_name = ?, gender = ?::gender, dob = to_date(?, 'YYYY-MM-DD'), address = ?, oms_number = ? WHERE id = ? RETURNING *"
              (get patient :full_name)
              (get patient :gender)
              (str (get patient :dob))
              (get patient :address)
              (get patient :oms_number)
              id
              ])))

(defn delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [id]
  (log/info (str "repo delete, id : " id))
  (db-exec ["DELETE FROM patients WHERE id = ?" id]))

