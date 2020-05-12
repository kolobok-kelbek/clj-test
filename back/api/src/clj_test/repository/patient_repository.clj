(ns clj-test.repository.patient-repository
  (:require [clj-test.config.db :as db]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]
            [clojure.tools.logging :as log]
            [clj-test.model.patient :as patient-model]))

(def table-name :patients)

(defn prepare-patient
  [patient]
  (update patient :dob str))

(defn patient-query-fields
  []
  {:full_name :?full_name
    :gender (sql/call :to_gender :?gender)
    :dob (sql/call :to_date :?dob "YYYY-MM-DD")
    :address :?address
    :oms_number :?oms_number})

(defn get-list
  "Gets list patients from the database"
  [spec-query]
  (let [query (spec-query table-name)]
    (log/info (str "method get-list; sql query - " query))
    (db/query query)))

(defn get-single
  "Gets only one patient using its id"
  [spec-query]
  (let [query (spec-query table-name)]
    (log/info (str "method get-single; sql query - " query))
    (first (db/query (spec-query table-name)))))

(defn patient-create
  "Adds a new patient to the database"
  [spec-query]
  (let [query (spec-query table-name)]
    (log/info (str "method patient-create; sql query - " query))
    (first (db/query query))))

(defn patient-update
  "Updates an existing patient on the database"
  [spec-query]
  (let [query (spec-query table-name)] 
    (log/info (str "method patient-update; sql query - " query))
    (first (db/query query))))

(defn patient-delete
  "Deletes an existing patient on the database, returns id of deleted patient"
  [spec-query]
  (let [query (spec-query table-name)]
    (log/info (str "method patient-delete; sql query - " query))
    (db/exec query)))

